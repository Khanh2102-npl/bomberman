# Bomberman Game - OOP Project

## Mô tả dự án

Bomberman là một trò chơi hành động kinh điển, nơi người chơi điều khiển nhân vật Bomber để đặt bom và tiêu diệt kẻ địch. Mục tiêu của trò chơi là tiêu diệt tất cả Enemy trong màn chơi và tìm ra Portal để qua màn tiếp theo.

Trong bài tập lớn này, chúng ta sẽ triển khai trò chơi Bomberman bằng ngôn ngữ Java, áp dụng các nguyên tắc của Lập trình Hướng Đối Tượng (OOP).

---

## Các đối tượng trong trò chơi

### **1. Bomber (Nhân vật chính)**

* Bomber là nhân vật chính do người chơi điều khiển.
* Bomber có thể di chuyển theo 4 hướng (trái, phải, lên, xuống).
* Bomber có thể đặt bom trên các ô Grass và di chuyển ra khỏi vị trí đặt bom ngay lập tức.
* Bomber sẽ chết nếu va chạm với Enemy hoặc trong phạm vi nổ của Bomb.

### **2. Enemy (Kẻ địch)**

* Enemy là những đối tượng mà Bomber phải tiêu diệt.
* Có hai loại Enemy:

  * **Balloom:** Di chuyển ngẫu nhiên với tốc độ cố định.
  * **Oneal:** Có khả năng thay đổi tốc độ và đuổi theo Bomber.

### **3. Bomb (Bom)**

* Bomb được đặt tại vị trí của Bomber trên các ô Grass.
* Sau 2 giây, Bomb sẽ phát nổ và tạo ra các Flame (tia lửa).
* Bomb có thể kích hoạt các Bomb khác trong phạm vi nổ.

### **4. Grass (Bãi cỏ)**

* Là bề mặt mà Bomber và Enemy có thể di chuyển qua.
* Cho phép đặt Bomb lên trên.

### **5. Wall (Tường cố định)**

* Là vật thể cố định, không thể phá hủy.
* Không thể đặt Bomb lên và không thể di chuyển qua.

### **6. Brick (Gạch)**

* Là vật cản có thể bị phá hủy bởi Bomb.
* Khi Brick bị phá hủy, có thể để lộ Portal hoặc Item.

### **7. Portal (Cổng qua màn)**

* Được giấu phía sau Brick.
* Chỉ xuất hiện khi Brick bị phá hủy.
* Người chơi có thể qua màn mới nếu tất cả Enemy đã bị tiêu diệt.

### **8. Item (Vật phẩm hỗ trợ)**

* Được giấu sau Brick và chỉ xuất hiện khi Brick bị phá hủy.
* Các Item bao gồm:

  * **SpeedItem:** Tăng tốc độ di chuyển của Bomber.
  * **FlameItem:** Tăng độ dài của các Flame khi Bomb nổ.
  * **BombItem:** Tăng số lượng Bomb có thể đặt cùng lúc.

---

## Cách chơi

* Di chuyển Bomber bằng các phím mũi tên (trái, phải, lên, xuống).
* Đặt Bomb bằng phím Space.
* Mục tiêu: Tiêu diệt tất cả Enemy và tìm Portal để qua màn.
* Bomber sẽ chết nếu va chạm với Enemy hoặc trong phạm vi nổ của Bomb.

---

## Xử lý va chạm và Bomb nổ

* Khi Bomb phát nổ, các Flame sẽ xuất hiện theo bốn hướng (trên, dưới, trái, phải).
* Độ dài của Flame mặc định là 1 đơn vị và có thể tăng lên khi sử dụng FlameItem.
* Nếu Flame gặp vật cản như Wall, Brick hoặc Bomb khác, nó sẽ dừng lại tại đó.
* Bomb khác trong phạm vi Flame sẽ phát nổ ngay lập tức.
* Enemy sẽ bị tiêu diệt khi va chạm với Flame.

---

## Cài đặt và chạy chương trình

* Clone repository:

  ```bash
  git clone https://github.com/Khanh2102-npl/bomberman.git
  cd Bomberman-Game
  ```
* Compile chương trình:

  ```bash
  javac Main.java
  ```
* Chạy chương trình:

  ```bash
  java Main
  ```

---

## Yêu cầu kỹ thuật

* Java JDK 8 trở lên.
* IDE: IntelliJ, Eclipse hoặc NetBeans.

---

## Đóng góp và phát triển

* Đóng góp thêm các loại Enemy, Item mới để tăng độ khó và sự phong phú cho trò chơi.
* Cải tiến AI của Enemy để tăng tính thử thách.
* Thêm các màn chơi mới và cập nhật đồ họa.

---

## Tác giả

* 

---

## Giấy phép

* Dự án này tuân theo giấy phép MIT.
