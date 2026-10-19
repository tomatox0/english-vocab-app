package com.example.english_vocab.controller;

import com.example.english_vocab.entity.Word;
import com.example.english_vocab.repository.WordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;

@Controller
public class WordController {
    private final WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("word", new Word());
        model.addAttribute("wordList", wordRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String registerWord(@ModelAttribute Word word) {
        wordRepository.save(word);
        return "redirect:/register";
    }

    @PostMapping("/delete/{id}")
    public String deleteWord(@PathVariable Long id) {
        wordRepository.deleteById(id);

        return "redirect:/register";
    }

    @GetMapping("/study")
    public String studyMode(
            @RequestParam(name = "mode", defaultValue = "enToJp") String mode,
            HttpSession session, Model model) {

        if (!"enToJp".equals(mode) && !"jpToEn".equals(mode)) {
            mode = "enToJp";
        }

        @SuppressWarnings("unchecked")
        List<Word> unshownWords = (List<Word>) session.getAttribute("unshownWords");

        if (unshownWords == null || unshownWords.isEmpty()) {
            unshownWords = wordRepository.findAll();
            Collections.shuffle(unshownWords);
        }

        Word displayWord = null;
        if (!unshownWords.isEmpty()) {
            displayWord = unshownWords.remove(0);
            session.setAttribute("unshownWords", unshownWords);
        }

        model.addAttribute("word", displayWord);
        model.addAttribute("mode", mode);

        return "study";
    }

    @GetMapping("/test")
    public String showTestSettings(Model model) {
        long totalWords = wordRepository.count();
        model.addAttribute("totalWords", totalWords);
        return "test-settings";
    }

    @GetMapping("/test/play")
    public String playTest(@RequestParam(name = "limit", defaultValue = "10") int limit, Model model) {
        List<Word> testWords = wordRepository.findRandomWords(limit);

        model.addAttribute("testWords", testWords);
        model.addAttribute("limit", limit);
        return "test-play";
    }
}
