# Cấu trúc backend Gatherly

## Quyết định

Dùng một ứng dụng Spring Boot, chia package theo tính năng (package by feature).
Trong mỗi tính năng, phân lớp Controller → Service → Repository.
Đây là lựa chọn cho giai đoạn học và xây MVP hiện tại; chưa cần microservices,
Spring Modulith hay các lớp interface/implementation chỉ để đủ khuôn mẫu.

`BackendApplication` nằm trong package gốc `com.gatherly`, để Spring tự quét
các component và entity bên dưới.

## Cấu trúc khi có tính năng đầu tiên

Ví dụ dưới đây minh họa cách đặt file, chưa chốt nghiệp vụ sự kiện:

```text
backend/src/main/java/com/gatherly/
├── BackendApplication.java
├── package-info.java
└── event/
    ├── controller/
    │   └── EventController.java
    ├── dto/
    │   ├── CreateEventRequest.java
    │   └── EventResponse.java
    ├── service/
    │   └── EventService.java
    ├── model/
    │   └── Event.java
    └── repository/
        └── EventRepository.java
```

Chỉ tạo package khi có code thực sự cần nó. Hiện chưa có tính năng nghiệp vụ,
nên bộ khung không tạo các Controller/Service/Repository rỗng.

## Trách nhiệm

- **controller:** nhận HTTP, nhận DTO, gọi service, trả status và response DTO.
- **dto:** dữ liệu đầu vào/đầu ra API; ưu tiên record khi phù hợp. Không trả
  trực tiếp JPA entity ra HTTP.
- **service:** nghiệp vụ, kiểm tra quyền trên tài nguyên và transaction khi cần.
  Dùng constructor injection. Chưa cần interface riêng cho mọi service.
- **model:** đối tượng nghiệp vụ, enum và JPA entity khi đến phần persistence.
  Entity là class; không dùng record làm JPA entity.
- **repository:** truy cập dữ liệu. Phase 07 dùng bộ nhớ; Phase 10 chuyển sang
  Spring Data JPA. Không thêm JpaRepository khi chưa có entity và bảng.
- **config/common:** chỉ bổ sung khi có cấu hình hoặc code thực sự dùng chung;
  không gom toàn bộ nghiệp vụ vào một package `common`.

Không tạo các package `controller`, `service`, `repository` dùng chung cho mọi
tính năng ở gốc. Ví dụ chức năng tài khoản sau này nằm trong `com.gatherly.account`.
Test đặt dưới `src/test/java`, phản chiếu package của code được kiểm tra.

## Quy ước ban đầu

- API nghiệp vụ mới dùng tiền tố `/api/v1` và tên tài nguyên số nhiều.
- Các endpoint bài luyện `/api/ping` và `/api/hello` đã được bỏ.
- Kiểm tra tình trạng ứng dụng qua `GET /actuator/health`.
- Dùng SLF4J cho log; không dùng `System.out.println` để ghi log nghiệp vụ.
- Giữ `spring.jpa.open-in-view=false` và `ddl-auto=none`.
- Khi bắt đầu tạo bảng ở Phase 09–10, thêm Flyway và migration đầu tiên;
  không để Hibernate tự sửa schema bằng `update`.
- Không thêm authentication, queue, cache, Lombok hay thư viện mapping khi
  chưa có nhu cầu cụ thể. Học và kiểm tra từng phần bằng chức năng thực tế.

## Tài liệu nền

- [Spring Boot: cấu trúc code](https://docs.spring.io/spring-boot/reference/using/structuring-your-code.html)
- [Spring Boot: cấu hình bên ngoài](https://docs.spring.io/spring-boot/reference/features/external-config.html)
