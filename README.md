# Gatherly

Project học Java và Spring Boot thông qua việc xây một ứng dụng thực tế.
Hiện đã chuẩn bị bộ khung backend; phạm vi nghiệp vụ cần được chốt trước Phase 07.

## Công nghệ

- JDK 21.
- Spring Boot 4.1.1, Spring MVC, Validation, Spring Data JPA, Actuator.
- Maven 3.9.16 qua Maven Wrapper; không cần cài Maven toàn máy.
- PostgreSQL 17 qua Docker Compose, cổng local mặc định `5433`.
- JUnit/AssertJ cho test; DevTools cho quá trình phát triển.

## Chuẩn bị lần đầu trên Windows

1. Cài JDK 21 và đặt `JAVA_HOME` trỏ đến thư mục JDK, không phải thư mục `bin`.
2. Cài và mở Docker Desktop, dùng Linux containers.
3. Tại thư mục gốc project, tạo cấu hình local nếu chưa có:

   ```powershell
   if (-not (Test-Path .env)) { Copy-Item .env.example .env }
   ```

4. Mở `.env`, đặt mật khẩu local trước khi tạo database lần đầu.
   File này không được commit. Dùng giá trị đơn giản theo hướng dẫn trong
   `.env.example` để tương thích cả Compose và Java properties.

Với database đã có volume, giữ thông tin kết nối hiện tại. Sửa mật khẩu trong
`.env` không đổi mật khẩu của PostgreSQL đã khởi tạo. Không xóa volume để sửa lỗi
kết nối; cần đổi mật khẩu bằng thao tác quản trị database khi có nhu cầu.

Spring đọc `.env` ở gốc project khi chạy từ gốc hoặc từ `backend/`.
Biến môi trường hệ điều hành có ưu tiên cao hơn file này.
`DB_URL` có thể ghi đè JDBC URL; thông tin đăng nhập dùng `POSTGRES_USER` và
`POSTGRES_PASSWORD` cho cả backend và Compose.

## Chạy và kiểm tra

Chạy các lệnh sau từ thư mục gốc:

```powershell
# Xác nhận Maven và JDK mà project sử dụng.
.\scripts\backend.ps1 -Task version

# Khởi động database và đợi healthcheck.
docker compose up -d --wait postgres

# Build sạch và chạy test (cần database local đang hoạt động).
.\scripts\backend.ps1 -Task verify

# Chạy backend ở terminal hiện tại; Ctrl+C để dừng.
.\scripts\backend.ps1 -Task run
```

Mở một terminal khác:

```powershell
Invoke-RestMethod http://127.0.0.1:8080/actuator/health
```

Kết quả mong đợi là `status: UP`. Chỉ endpoint health được expose, không hiển thị
chi tiết hạ tầng. `/` trả 404 là bình thường vì chưa có trang chủ hoặc API nghiệp vụ.

Script chọn Java từ `JAVA_HOME` và ưu tiên JDK 21 trong PATH của lần chạy đó,
không thay đổi cấu hình Java toàn máy. VS Code cũng ưu tiên JDK trong `JAVA_HOME`
cho **terminal mới** của workspace. Nếu terminal cũ vẫn báo Java 8, đóng terminal
đó và mở terminal mới, hoặc dùng script trên.

Nếu PowerShell chặn script, có thể dùng lệnh trực tiếp sau từ `backend/`:

```powershell
.\mvnw.cmd -version
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run
```

Maven Wrapper sử dụng `JAVA_HOME`. Lần build đầu cần mạng để tải dependency.

## Phát triển

- [Cấu trúc backend và quy ước đặt file](docs/backend-architecture.md).
- Source ứng dụng nằm trong `backend/src/main/java/com/gatherly`.
- Test nền kiểm tra khởi động ứng dụng, kết nối PostgreSQL, HTTP health và việc
  các endpoint bài luyện đã được loại bỏ. Các test không tạo/xóa bảng nghiệp vụ.
- DevTools có thể khởi động lại ứng dụng khi **classpath đã được cập nhật**.
  Lưu file chưa chắc đã biên dịch lại: tùy IDE, cần Build Project hoặc chạy lại.
- Nếu cổng 8080 đang bị chiếm, dừng phiên backend cũ trong IDE trước khi chạy.
- Dừng database bằng `docker compose stop postgres`; dữ liệu trong volume được giữ.

## Các phần làm tiếp theo

Chốt người dùng và chức năng đầu tiên → Phase 07 CRUD trong bộ nhớ → validation
và lỗi → SQL/migration/JPA. Database hiện chưa cần có bảng nghiệp vụ.
CI/CD và triển khai sẽ được thực hiện ở phase tương ứng, chưa được cấu hình ở bước này.
