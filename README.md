# QR-Order B2C

Hệ thống gọi món bằng QR Code dành cho nhà hàng theo mô hình B2C (Business-to-Consumer).

---

## Mục tiêu

Xây dựng nền tảng giúp khách hàng:

* Quét mã QR tại bàn
* Xem menu điện tử
* Gọi món trực tiếp trên điện thoại
* Theo dõi trạng thái món ăn
* Thanh toán và đánh giá dịch vụ

Đồng thời hỗ trợ nhà hàng:

* Quản lý bàn ăn
* Quản lý thực đơn
* Quản lý đơn hàng
* Quản lý nhân viên
* Theo dõi doanh thu

---

## Công nghệ sử dụng

### Backend

* Java 21
* Spring Boot
* Spring Security JWT
* Spring Data JPA
* Hibernate
* MySQL
* Maven

### Frontend

* React
* Vite
* Tailwind CSS

---

## Chức năng hệ thống

### Customer

* Scan QR tại bàn
* Xem menu
* Gọi món
* Theo dõi trạng thái món
* Đánh giá dịch vụ

### Waiter

* Check-in khách đặt bàn
* Phục vụ món ăn
* Hủy món lỗi
* Xử lý món bị hỏng

### Kitchen

* Nhận đơn hàng
* Chuyển trạng thái:

    * PENDING
    * PREPARING
    * DONE

### Cashier

* Xem hóa đơn
* Thanh toán
* Xem lịch sử thanh toán

### Admin

* Dashboard
* User Management
* Food Management
* Category Management
* Table Management

---

## Luồng khách vãng lai

Scan QR

↓

Tạo Table Session

↓

Gọi món

↓

Bếp xử lý

↓

Phục vụ

↓

Thanh toán

↓

Đánh giá

---

## Luồng khách đặt bàn

Đặt bàn

↓

Check-in

↓

Table Session

↓

Quét QR

↓

Gọi món

↓

Bếp xử lý

↓

Thanh toán

↓

Đánh giá

---

## Các vai trò

| Role    | Mô tả             |
| ------- | ----------------- |
| ADMIN   | Quản trị hệ thống |
| WAITER  | Nhân viên phục vụ |
| KITCHEN | Nhân viên bếp     |
| CASHIER | Thu ngân          |

---

## Tài khoản mẫu

### Admin

Username:

admin

Password:

admin123

### Waiter

Username:

waiter

Password:

waiter123

### Kitchen

Username:

kitchen

Password:

kitchen123

### Cashier

Username:

cashier

Password:

cashier123

---

## Cách chạy dự án

### Clone project

```bash
git clone https://github.com/your-username/qr-order-b2c.git
```

### Tạo database

```sql
CREATE DATABASE qr_order;
```

### Cấu hình database

Chỉnh file:

```properties
application.properties
```

### Chạy dự án

```bash
mvn spring-boot:run
```

---

## Kiến trúc hệ thống

Customer

↓

CustomerController

↓

Service Layer

↓

Repository Layer

↓

MySQL Database

---

## Trạng thái dự án

Backend MVP hoàn thành.

Đang phát triển Frontend React + Vite.
