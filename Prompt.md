# Kotlin Spring Boot 認証システム - プロジェクトガイド

**生成日:** 2025年10月23日  
**バージョン:** 1.0-SNAPSHOT  
**フレームワーク:** Spring Boot 3.4.0 + Kotlin 2.2.20

---

## 📋 目次

1. [プロジェクト概要](#プロジェクト概要)
2. [技術スタック](#技術スタック)
3. [システムアーキテクチャ](#システムアーキテクチャ)
4. [プロジェクト構成](#プロジェクト構成)
5. [コア機能](#コア機能)
6. [設定説明](#設定説明)
7. [API エンドポイント](#api-エンドポイント)
8. [データベース設計](#データベース設計)
9. [セキュリティ機構](#セキュリティ機構)
10. [テスト戦略](#テスト戦略)
11. [クイックスタート](#クイックスタート)
12. [開発ガイド](#開発ガイド)
13. [デプロイ手順](#デプロイ手順)
14. [よくある質問](#よくある質問)

---

## プロジェクト概要

### プロジェクト紹介
Kotlin と Spring Boot 3.4.0 を用いて構築されたモダンな認証システム。安全なユーザーログイン、セッション管理、ロールベースのアクセス制御を提供します。

### コア機能
- ✅ **安全な認証**: BCrypt パスワードハッシュ（自動ソルト）
- ✅ **セッション管理**: 30分タイムアウト、Remember-Me 機能（24時間）
- ✅ **ロール管理**: USER と ADMIN の2ロール
- ✅ **永続化**: PostgreSQL + Spring Data JPA
- ✅ **サーバーサイドレンダリング**: Thymeleaf テンプレート + Bootstrap 5
- ✅ **テスト充実**: 主要機能をカバーする14件のユニットテスト
- ✅ **モダンビルド**: Gradle 8.11.1（Gradle 9.0 をサポート）

### 適用シナリオ
- Web アプリのユーザー認証システム
- 企業向け内部管理システム
- Spring Security と Kotlin の学習用サンプル
- マイクロサービス構成での認証サービス基盤

---

## 技術スタック

### バックエンドフレームワーク
| 技術 | バージョン | 用途 |
|------|------------|------|
| Kotlin | 2.2.20 | メイン言語 |
| Spring Boot | 3.4.0 | アプリケーションフレームワーク |
| Spring Security | 6.x | 認証・認可 |
| Spring Data JPA | 3.x | データアクセス |
| Hibernate | 6.x | ORM |

### データベース
- **PostgreSQL** - リレーショナルデータベース
- **HikariCP** - コネクションプール（デフォルト）

### フロントエンド
- **Thymeleaf** - サーバーサイドテンプレート
- **Bootstrap 5.3.0** - UI フレームワーク
- **HTML5/CSS3** - 標準フロントエンド技術

### テスト
- **JUnit 5** - 単体テストフレームワーク
- **Spring Boot Test** - 統合テストサポート
- **Spring Security Test** - セキュリティテスト用ツール

### ビルドツール
- **Gradle 8.11.1** - ビルドツール（Kotlin DSL）
- **Java 21** - 実行環境

---

## システムアーキテクチャ

### レイヤードアーキテクチャ

```
┌─────────────────────────────────────────┐
│         プレゼンテーション層 (Presentation)    │
│    Controllers + Thymeleaf Views          │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         サービス層 (Service)                │
│    ビジネスロジック + パスワードハッシュ + 認証  │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         データアクセス層 (Repository)       │
│        Spring Data JPA インターフェース     │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│         データ層 (Database)               │
│          PostgreSQL データベース          │
└─────────────────────────────────────────┘
```

### リクエストフロー

```
1. ユーザーがログインフォームを送信
   ↓
2. AuthController が POST リクエストを受け取る
   ↓
3. LoginRequest DTO を検証
   ↓
4. AuthService が認証ロジックを実行
   ↓
5. PasswordService がパスワードを検証
   ↓
6. UserRepository がデータベース照会
   ↓
7. Spring Security がセッションを作成
   ↓
8. Dashboard にリダイレクト
```

### セキュリティフロー

```
パスワード保存: 平文 → BCrypt(パスワード + ソルト) → DBに保存
パスワード検証: ユーザー入力 → BCrypt 検証 → 一致すれば認証成功
セッション管理: ログイン成功 → セッション作成 → タイムアウト設定
Remember-Me: チェック有り → トークン生成 → Cookie に保存（24時間）
```

---

## プロジェクト構成

```
testkotlin/
├── src/main/kotlin/com/example/auth/
│   ├── Application.kt                    # アプリケーション入口
│   ├── config/
│   │   └── SecurityConfig.kt             # Spring Security 設定
│   ├── controller/
│   │   └── AuthController.kt             # 認証コントローラ
│   ├── dto/
│   │   └── LoginRequest.kt               # ログインリクエスト DTO
│   ├── entity/
│   │   ├── User.kt                       # ユーザーエンティティ
│   │   └── Role.kt                       # ロール列挙
│   ├── repository/
│   │   └── UserRepository.kt             # ユーザーレポジトリ
│   └── service/
│       ├── AuthService.kt                # 認証サービス
│       └── PasswordService.kt            # パスワードサービス
│
├── src/main/resources/
│   ├── application.yml                    # アプリ設定
│   ├── static/css/
│   │   └── styles.css                     # カスタムスタイル
│   └── templates/
│       ├── login.html                     # ログインページ
│       ├── dashboard.html                 # ダッシュボード
│       └── fragments/                     # テンプレート断片
│           ├── header.html
│           └── footer.html
│
├── src/test/kotlin/com/example/auth/
│   └── service/
│       └── PasswordServiceTest.kt        # パスワードサービスのテスト
│
├── build.gradle.kts                       # Gradle ビルド設定
├── settings.gradle.kts                    # Gradle 設定
├── README.md                              # プロジェクトドキュメント
└── PROJECT_GUIDE.md                       # 本ファイル
```

### コード構成の説明

- **config/** - Spring の設定クラス（Security 設定を含む）
- **controller/** - MVC コントローラ、HTTP リクエストを処理
- **dto/** - DTO（データ転送オブジェクト）、フロントとバック間のやり取りに使用
- **entity/** - JPA エンティティ、DB テーブルにマッピング
- **repository/** - データアクセスインターフェース（JpaRepository 継承）
- **service/** - ビジネスロジック層

---

## コア機能

### 1. ユーザー認証
- **ログイン検証**: ユーザー名/パスワード検証、BCrypt ハッシュで比較
- **セッション管理**: セッションベース認証、30分タイムアウト
- **Remember-Me**: チェックで24時間の保持
- **ログアウト**: セッション削除、ログインページへリダイレクト

### 2. パスワードセキュリティ
- **ハッシュアルゴリズム**: BCrypt（コストファクター: 10）
- **ソルト処理**: 各パスワードに対してユニークなソルトを自動生成
- **セキュア比較**: 時間差攻撃を避ける一定時間比較
- **パスワード強度**: 特殊文字・大文字小文字をサポート

### 3. ロール認可
- **ロール種類**: USER（一般ユーザー）、ADMIN（管理者）
- **アクセス制御**: ロールベースでページアクセス権を管理
- **権限継承**: Spring Security の権限体系を利用

### 4. データ永続化
- **ORM**: Hibernate 6.x
- **自動テーブル作成**: ddl-auto: update
- **トランザクション管理**: Spring の宣言的トランザクション
- **コネクションプール**: HikariCP

### 5. フロント表示
- **テンプレートエンジン**: Thymeleaf 3.x
- **レスポンシブデザイン**: Bootstrap 5 でモバイル対応
- **フォーム検証**: フロントの必須チェック + バックの @Valid 検証
- **エラーメッセージ**: Flash Attributes を用いて表示

---

## 設定説明

### アプリ設定 (application.yml)

**データベース設定**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://192.168.135.128:5432/auth_system
    username: postgres
    password: kawasaki
```
*本番環境では環境変数の使用を推奨*

**JPA 設定**
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update      # 本番では validate に変更
    show-sql: true          # 本番では false に変更
```

**サーバ設定**
```yaml
server:
  port: 8080
  servlet:
    session:
      timeout: 30m          # セッションタイムアウト
```

### セキュリティ設定の要点

**SecurityConfig.kt の主な設定:**
- 公開パス: `/login`, `/static/**`
- 保護対象: その他すべて
- ログインページ: `/login`
- 成功後リダイレクト: `/dashboard`
- ログアウト後: `/login?logout`
- Remember-Me: 24時間有効

---

## API エンドポイント

### 認証関連 API

| メソッド | パス | 説明 | 権限 | 戻り値 |
|------|------|------|------|------|
| GET | `/login` | ログインページを表示 | 公開 | HTML ページ |
| POST | `/login` | ログイン処理 | 公開 | リダイレクト |
| GET | `/logout` | ログアウト処理 | 認証必要 | ログインページへリダイレクト |
| GET | `/dashboard` | ユーザーダッシュボード | 認証必要 | HTML ページ |

### リクエスト例

**ログインリクエスト**
```
POST /login
Content-Type: application/x-www-form-urlencoded

username=admin&password=admin123&rememberMe=true
```

**レスポンスフロー**
- 成功: 302 リダイレクト `/dashboard`
- 失敗: 302 リダイレクト `/login?error`

---

## データベース設計

### users テーブル構造

| カラム | 型 | 制約 | 説明 |
|------|------|------|------|
| id | BIGSERIAL | PRIMARY KEY | 自動増分主キー |
| username | VARCHAR(255) | UNIQUE, NOT NULL | ユーザー名 |
| email | VARCHAR(255) | UNIQUE, NOT NULL | メールアドレス |
| password | VARCHAR(255) | NOT NULL | BCrypt ハッシュされたパスワード |
| role | VARCHAR(50) | NOT NULL | ユーザーロール（USER/ADMIN）|
| created_at | TIMESTAMP | NOT NULL | 作成日時 |

### インデックス設計
```sql
CREATE UNIQUE INDEX idx_users_username ON users(username);
CREATE UNIQUE INDEX idx_users_email ON users(email);
```

### サンプルデータ
```sql
-- 管理者アカウント作成（パスワード: admin123）
INSERT INTO users (username, email, password, role, created_at)
VALUES (
    'admin',
    'admin@example.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMIN',
    CURRENT_TIMESTAMP
);
```

---

## セキュリティ機構

### 実装済みのセキュリティ対策
✅ **パスワード保護**
- BCrypt ハッシュ（コスト: 10）
- 自動ソルト生成
- 一定時間比較で時差攻撃を防止

✅ **セッション保護**
- セッションタイムアウト（30分）
- Remember-Me トークン（24時間）
- セキュアな Cookie 設定

✅ **注入対策**
- JPA パラメータ化クエリで SQL インジェクションを防止
- Thymeleaf の自動エスケープで XSS を軽減

✅ **認証・認可**
- Spring Security フレームワーク
- ロールベースのアクセス制御
- ログイン状態の確認

### 本番環境での推奨設定
⚠️ **必ず有効化すべき項目:**
- CSRF 保護（本プロジェクトでは現在無効）
- HTTPS/TLS による通信暗号化
- ログイン失敗回数制限
- アカウントロック機能

⚠️ **推奨設定:**
- 機密情報は環境変数で管理
- SQL のプリペアドステートメントを有効化
- Cookie の安全フラグ（HttpOnly, Secure, SameSite）を設定
- 監査ログの導入

⚠️ **パスワードポリシー:**
- 最小長（推奨 8 文字以上）
- 複雑性（大文字、小文字、数字、特殊文字）
- パスワード履歴管理
- 定期変更のリマインダー

---

## テスト戦略

### テストカバレッジ

**PasswordServiceTest.kt** - 14 件のテストケース

1. **パスワードエンコードのテスト** (5件)
   - 基本的なエンコード機能
   - ソルトのユニーク性検証
   - 空パスワード処理
   - 特殊文字のサポート
   - 長いパスワードの処理

2. **パスワードマッチングテスト** (7件)
   - 正しいパスワードの検証
   - 誤ったパスワードは拒否
   - 空パスワードの境界ケース
   - 大文字小文字の区別
   - 特殊文字のマッチング
   - 微妙な差異の検出

3. **統合テスト** (2件)
   - エンコード/マッチングのフルワークフロー
   - 複数回の一貫性確認

### テストコマンド

```bash
# 全テストを実行
./gradlew test

# 特定のテストクラスを実行
./gradlew test --tests "PasswordServiceTest"

# テストレポートを表示
open build/reports/tests/test/index.html

# 継続テスト（ファイル変更を監視）
./gradlew test --continuous
```

### テストのベストプラクティス
- JUnit 5 の `@Nested` でテストを整理
- Given-When-Then パターンを採用
- 説明的なテスト名を付与
- 境界条件と例外ケースをテスト

---

## クイックスタート

### 前提条件
- Java 21 以上
- PostgreSQL データベース
- Gradle 8.11+（付属の gradlew を使用可）

### 1. リポジトリをクローン
```bash
git clone <repository-url>
cd testkotlin
```

### 2. データベース設定
```bash
# データベースを作成
psql -U postgres -c "CREATE DATABASE auth_system;"

# application.yml の DB 接続情報を編集
```

### 3. ビルド
```bash
# クリーン＆ビルド
./gradlew clean build

# テストをスキップしてビルド（高速化）
./gradlew build -x test
```

### 4. アプリを実行
```bash
# Gradle で実行
./gradlew bootRun

# または JAR を実行
java -jar build/libs/testkotlin-1.0-SNAPSHOT.jar
```

### 5. アクセス
ブラウザで開く: `http://localhost:8080`

### 6. テストユーザー作成
```sql
-- DB にテストユーザーを挿入（パスワード: test123）
INSERT INTO users (username, email, password, role, created_at)
VALUES (
    'testuser',
    'test@example.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'USER',
    CURRENT_TIMESTAMP
);
```

---

## 開発ガイド

### 開発環境の設定

**推奨 IDE:**
- IntelliJ IDEA Ultimate（推奨）
- IntelliJ IDEA Community + Kotlin プラグイン

**必須プラグイン:**
- Kotlin
- Spring Boot
- Gradle

### よく使うコマンド

**ビルド関連:**
```bash
./gradlew clean          # ビルドファイルをクリーン
./gradlew build          # フルビルド
./gradlew compileKotlin  # Kotlin のみコンパイル
./gradlew dependencies   # 依存関係ツリーを表示
```

**実行関連:**
```bash
./gradlew bootRun                                    # アプリ実行
./gradlew bootRun --args='--spring.profiles.active=dev'  # プロファイル指定で実行
```

**テスト関連:**
```bash
./gradlew test              # テストを実行
./gradlew test --info       # 詳細ログ
./gradlew test --stacktrace # スタックトレースを表示
```

### 機能追加手順

**1. 新しいエンティティ追加**
```
1. entity/ にエンティティクラスを作成
2. repository/ に Repository インターフェースを作成
3. service/ にビジネスロジックを実装
4. controller/ にエンドポイントを追加
5. 対応する Thymeleaf テンプレートを作成
```

**2. 新しいセキュリティルール追加**
```
SecurityConfig.kt の securityFilterChain を修正
新しい requestMatchers ルールを追加
```

**3. 新しいロール追加**
```
1. Role.kt 列挙にロールを追加
2. DB スキーマを更新
3. SecurityConfig で権限を設定
```

### コード規約

**Kotlin のスタイル:**
- DTO は data class を使用
- 不変変数（val）を優先
- 命名引数で可読性向上
- 適宜拡張関数を利用

**Spring Boot のベストプラクティス:**
- フィールド注入ではなくコンストラクタ注入を利用
- Service 層でビジネスロジックを処理
- Controller はリクエスト処理に専念
- DTO を用いてエンティティを直接露出しない

---

## デプロイ手順

### 本番環境設定

**1. 環境変数設定**
```bash
export DB_URL=jdbc:postgresql://prod-db:5432/auth_system
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export SERVER_PORT=8080
```

**2. application.yml を修正**
```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate  # 本番では validate
    show-sql: false       # SQL ログを無効
```

**3. CSRF を有効化**
```kotlin
// SecurityConfig.kt 内で
.csrf { csrf ->
    csrf.enable()  // CSRF を有効化
}
```

### パッケージングとデプロイ

**実行可能 JAR のビルド:**
```bash
./gradlew clean build -x test
```

**Docker デプロイ（例）:**
```dockerfile
FROM eclipse-temurin:21-jre
COPY build/libs/testkotlin-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

**Systemd サービス（Linux）:**
```ini
[Unit]
Description=Auth System
After=postgresql.service

[Service]
User=appuser
ExecStart=/usr/bin/java -jar /opt/testkotlin/app.jar
Restart=always

[Install]
WantedBy=multi-user.target
```

---

## よくある質問

### Q1: ポート 8080 が既に使用されている
**解決方法:**
```yaml
# application.yml を変更
server:
  port: 8081
```

### Q2: データベース接続に失敗する
**チェックリスト:**
- PostgreSQL が起動しているか
- データベース名が正しいか
- ユーザー名/パスワードが正しいか
- ファイアウォールが接続を許可しているか

### Q3: Gradle ビルドが失敗する
**対処法:**
```bash
# キャッシュをクリアして再ビルド
./gradlew clean build --refresh-dependencies
```

### Q4: テストが失敗する
**主な原因:**
- データベースが起動していない
- ポート競合
- 依存関係のバージョン衝突

**デバッグコマンド:**
```bash
./gradlew test --stacktrace --info
```

### Q5: ページスタイルが表示されない
**確認項目:**
- static リソースパスが正しいか
- SecurityConfig が `/static/**` を許可しているか
- ブラウザのコンソールにエラーが出ていないか

---

## パフォーマンス最適化提案

### データベース最適化
```yaml
spring:
  jpa:
    properties:
      hibernate:
        jdbc:
          batch_size: 20          # バルク挿入
        order_inserts: true       # 挿入順序の最適化
        order_updates: true       # 更新順序の最適化
```

### コネクションプール最適化
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
```

### キャッシュ戦略
```yaml
spring:
  thymeleaf:
    cache: true  # 本番環境でテンプレートキャッシュを有効
```

---

## 今後の改善計画

### 短期的目標
- [ ] ユーザー登録機能の追加
- [ ] パスワードリセット機能の実装
- [ ] メール確認機能の追加
- [ ] アカウントの有効化/無効化機能

### 中期的目標
- [ ] OAuth2 統合（Google, GitHub）
- [ ] JWT トークン認証のサポート
- [ ] REST API エンドポイントの追加
- [ ] 管理者パネルの実装

### 長期的目標
- [ ] 二要素認証（2FA）
- [ ] 監査ログシステム
- [ ] 細粒度なユーザー権限管理
- [ ] マイクロサービスへの移行

---

## 技術サポート

### 参考リンク
- [Spring Boot 公式ドキュメント](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security ドキュメント](https://docs.spring.io/spring-security/reference/)
- [Kotlin ドキュメント](https://kotlinlang.org/docs/home.html)
- [Gradle ユーザーガイド](https://docs.gradle.org/current/userguide/userguide.html)

### サポートを得る方法
- README.md を参照
- ソースコード内のコメントを確認
- 関連ドキュメントを参照
- Issue を提出してフィードバック

---

## バージョン履歴

**v1.0-SNAPSHOT** (2025-10-23)
- 初期リリース
- 基本的な認証機能
- BCrypt によるパスワード保護
- ロールベースの権限制御
- テストカバレッジを確保

---

**プロジェクト管理者:** Development Team  
**最終更新:** 2025年10月23日  
**ライセンス:** 教育およびデモ用途

---

