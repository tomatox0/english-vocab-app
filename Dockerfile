# Java 25の環境を用意
FROM eclipse-temurin:25-jdk
WORKDIR /app

# プロジェクトのファイルをすべてコピー
COPY . .

# アプリをビルド（組み立て）
RUN ./gradlew build -x test && rm build/libs/*-plain.jar

# Render用にポート8080を開放
EXPOSE 8080

# アプリの起動コマンド
CMD ["sh", "-c", "java -jar build/libs/*.jar"]