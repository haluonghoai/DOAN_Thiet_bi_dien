create database QuanLyBanHang;
create table tblQuyen
(
	iMaquyen int primary key identity(1,1) not null,
	sTenquyen nvarchar(100) not null
);

create table tblNguoiDung
(
	iManguoidung int primary key identity(1,1) not null,
	sHoten nvarchar(100) not null,
	sEmail nvarchar(200) unique not null,
	sDiachi nvarchar(300) not null,
	sSodienthoai varchar(15) unique not null,
	dNgaysinh date not null,
	sMatkhau varchar(50) not null,
	iMaquyen int not null constraint FK_iMaquyen_tblNguoiDung foreign key(iMaquyen) references tblQuyen(iMaquyen)
);

create table tblKhachHang
(
	iMakhachhang int primary key identity(1,1) not null,
	sHoten nvarchar(100) not null,
	sEmail nvarchar(200) unique not null,
	sDiachi nvarchar(300) not null,
	sSodienthoai varchar(15) unique not null,
	sTendangnhap nvarchar(100) not null,
	sMatkhau varchar(50) not null
);

create table tblDanhMucSanPham
(
	iMadanhmucsp int primary key identity(1,1) not null,
	sTendanhmucsp nvarchar(100) not null,
	bTinhtrangban bit not null
);

create table tblSanPham
(
	iId_sanpham int primary key identity(1,1) not null,
	sMasanpham varchar(100) unique not null,
	sTensanpham nvarchar(100) not null,
	fGiaban float not null,
	sAnhsanpham nvarchar(200) not null,
	bTinhtrang bit not null,
	sMota nvarchar(400),
	iSoluong int not null,
	sThongsokythuat nvarchar(300),
	iBaohanh int not null,
	sDonvitinh nvarchar(50) not null,
	iMadanhmucsp int not null constraint FK_iMadanhmucsp_tblSanPham foreign key(iMadanhmucsp) references tblDanhMucSanPham(iMadanhmucsp)
);

create table tblHinhThucThanhToan
(
	iMahinhthucthanhtoan int primary key identity(1,1) not null,
	sTenhinhthucthanhtoan nvarchar(100) not null
);

create table tblDonHang
(
	iMadonhang int primary key identity(1,1) not null,
	dThoigiandat datetime not null,
	bTrangthaithanhtoan bit not null,
	sGhichu nvarchar(300) not null,
	iManguoidung int not null constraint FK_iManguoidung_tblDonHang foreign key(iManguoidung) references tblNguoiDung(iManguoidung),
	iMakhachhang int not null constraint FK_iMakhachhang_tblDonHang foreign key(iMakhachhang) references tblKhachHang(iMakhachhang),
	iMahinhthucthanhtoan int not null constraint FK_iMahinthucthanhtoan foreign key(iMahinhthucthanhtoan) references tblHinhThucThanhToan(iMahinhthucthanhtoan),
	iMatrangthai int not null constraint FK_iMatrangthai foreign key(iMatrangthai) references tblTrangThaiDonHang(iMatrangthai)
);

alter table tblDonHang add bTrangthaithanhtoan bit not null default 0
alter table tblDonHang alter column iMatrangthai int not null
alter table tblDonHang add constraint FK_iMatrangthai_tblDonHang foreign key(iMatrangthai) references tblTrangThaiDonHang(iMatrangthai)

create table tblNhaCungCap
(
	iManhacungcap int primary key identity(1,1) not null,
	sTennhacungcap nvarchar(200) not null,
	sSodienthoai varchar(15) not null unique,
	sEmail nvarchar(200) not null unique,
	sDiachi nvarchar(300) not null,
);

create table tblPhieuNhap
(
	iMaphieunhap int primary key identity(1,1) not null,
	dThoigianlap datetime not null,
	sGhichu nvarchar(300),
	iManguoidung int not null constraint FK_iManguoidung_tblPhieuNhap foreign key(iManguoidung) references tblNguoiDung(iManguoidung),
	iManhacungcap int not null constraint FK_iManhacungcap_tblPhieuNhap foreign key(iManhacungcap) references tblNhaCungCap(iManhacungcap)
);
alter table tblPhieuNhap add bTinhtrangnhap bit not null default 0

create table tblChiTietPhieuNhap
(
	iMaphieunhap int not null,
	iId_sanpham int not null,
	iSoluong int not null,
	fDongia float not null,
	constraint PK_tblChiTietPhieuNhap primary key(iMaphieunhap,iId_sanpham)
);

create table tblChiTietDonHang
(
	iMadonhang int not null,
	iId_sanpham int not null,
	iSoluong int not null,
	constraint PK_tblChiTietDonHang primary key(iMadonhang,iId_sanpham)
);

create table tblTrangThaiDonHang
(
	iMatrangthai int not null identity(1,1) primary key,
	sTentrangthai nvarchar(200) not null
);

insert tblQuyen values(N'Quản lý')
insert tblQuyen values(N'Nhân viên')

---Quan ly danh mục---
create proc sp_select_danhmuc
as
select * from tblDanhMucSanPham

create proc sp_insert_danhmuc(@tendm nvarchar(100), @tinhtrangban bit)
as
insert tblDanhMucSanPham values(@tendm,@tinhtrangban)

exec sp_insert_danhmuc N'Dây điện',1
exec sp_insert_danhmuc N'Thiết bị chiếu sáng',1
exec sp_insert_danhmuc N'Cáp điện',1
exec sp_insert_danhmuc N'Thiết bị điện',1
exec sp_insert_danhmuc N'Ống luồn dây điện',1
exec sp_insert_danhmuc N'Dây cáp viễn thông',1
exec sp_insert_danhmuc N'Thiết bị trung thế đường dây',1
exec sp_insert_danhmuc N'Hệ thống tủ phân phối điện',1
exec sp_insert_danhmuc N'Ống nhựa thoát nước',1

alter proc sp_update_danhmuc(@madm int,@tendm nvarchar(100), @tinhtrangban bit)
as
update tblDanhMucSanPham set sTendanhmucsp = @tendm, bTinhtrangban = @tinhtrangban where iMadanhmucsp = @madm

create proc sp_delete_danhmuc(@madm int)
as
delete from tblDanhMucSanPham where iMadanhmucsp = @madm

---Số bản ghi---
create proc sp_select_banghi
as
select count(*) from tblDanhMucSanPham

---Quản lý sản phẩm---

alter proc sp_select_sanpham
as
select * from tblSanPham

alter proc sp_insert_sanpham
(
@masp varchar(100),@tensp nvarchar(100),@giaban float, @anh nvarchar(200), @tinhtrang bit, @mota nvarchar(400), @soluong int, @thongso nvarchar(300),
@baohanh int, @donvitinh nvarchar(50),@madm int)
as
begin
insert tblSanPham values(@masp,@tensp,@giaban,@anh,@tinhtrang,@mota,@soluong,@thongso,@baohanh,@donvitinh,@madm)
end

exec sp_insert_sanpham 'SP101',N'Dây điện Cadivi, dây đôi mềm dẹt VCmo 2x2.5',395000,N'admin/admin/images/anh_san_pham/day_dien.jpg',1,
N'Dòng sản phẩm công tắc Minerva hoàn toàn mới với thiết kế mặt tiếp xúc lớn, không viền giúp tối đa hóa không gian vận hành
nhờ loại bỏ sự gò bó của khung viền so với các dòng công tắc khác, tạo cảm giác vận hành hoàn hảo. Bề mặt của công tắc sử dụng chất
liệu cải tiến cho độ bóng mịn mượt mà, bo cong hài hòa với không gian. Đặc biệt góc đảo chiều bật tắt nhỏ giúp sản phẩm trông thanh mảnh, mỏng hơn',
50,N'Dòng sản phẩm: Minevar, mầu sắc: mầu xám kim, dòng điện định mức: 16A, điện áp định: 250VAC, tiêu chuẩn: JIS Nhật Bản',2,N'dây',1

exec sp_insert_sanpham 'SP104',N'Dây điện Cadivi, dây đôi mềm dẹt VCmo 2x0.75',395000,N'admin/admin/images/anh_san_pham/day_dien.jpg',1,
N'Dòng sản phẩm công tắc Minerva hoàn toàn mới với thiết kế mặt tiếp xúc lớn, không viền giúp tối đa hóa không gian vận hành
nhờ loại bỏ sự gò bó của khung viền so với các dòng công tắc khác, tạo cảm giác vận hành hoàn hảo. Bề mặt của công tắc sử dụng chất
liệu cải tiến cho độ bóng mịn mượt mà, bo cong hài hòa với không gian. Đặc biệt góc đảo chiều bật tắt nhỏ giúp sản phẩm trông thanh mảnh, mỏng hơn',
50,N'Dòng sản phẩm: Minevar, mầu sắc: mầu xám kim, dòng điện định mức: 16A, điện áp định: 250VAC, tiêu chuẩn: JIS Nhật Bản',2,N'dây',1

exec sp_insert_sanpham 'SP106',N'Dây điện Trần Phú, dây đơn mềm VCm 1x6.0',395000,N'admin/admin/images/anh_san_pham/2842_0_day_don_mem_tran_phu_1x6 (1).jpg',1,
N'Dòng sản phẩm công tắc Minerva hoàn toàn mới với thiết kế mặt tiếp xúc lớn, không viền giúp tối đa hóa không gian vận hành
nhờ loại bỏ sự gò bó của khung viền so với các dòng công tắc khác, tạo cảm giác vận hành hoàn hảo. Bề mặt của công tắc sử dụng chất
liệu cải tiến cho độ bóng mịn mượt mà, bo cong hài hòa với không gian. Đặc biệt góc đảo chiều bật tắt nhỏ giúp sản phẩm trông thanh mảnh, mỏng hơn',
50,N'Dòng sản phẩm: Minevar, mầu sắc: mầu xám kim, dòng điện định mức: 16A, điện áp định: 250VAC, tiêu chuẩn: JIS Nhật Bản',2,N'dây',1

create proc sp_update_sanpham
(
@idsp int,@masp varchar(100),@tensp nvarchar(100),@giaban float, @anh nvarchar(200), @tinhtrang bit, @mota nvarchar(400), @soluong int, @thongso nvarchar(300),
@baohanh int, @donvitinh nvarchar(50),@madm int
)
as
begin
	update tblSanPham set sMasanpham = @masp, sTensanpham = @tensp, fGiaban = @giaban, sAnhsanpham = @anh, bTinhtrang = @tinhtrang, sMota = @mota,
	iSoluong = @soluong, sThongsokythuat = @thongso, iBaohanh = @baohanh, sDonvitinh = @donvitinh, iMadanhmucsp = @madm where iId_sanpham = @idsp
end

create proc sp_delete_sanpham(@idsp int)
as
delete from tblSanPham where iId_sanpham = @idsp
 
 ---search sanpham---
alter proc sp_search_sp(@masp varchar(100), @tensp nvarchar(100), @giaban float, @tinhtrang bit)
as
 select * from tblSanPham where (sMasanpham = '@masp' or sMasanpham = '') 
 and (sTensanpham = @tensp or sTensanpham = '') 
 and (fGiaban = @giaban or fGiaban = '') and (bTinhtrang = @tinhtrang or bTinhtrang = '')

 exec sp_search_sp '',N'','','1'

 select * from tblSanPham where sMasanpham = '' or sTensanpham like N'Dây điện' or fGiaban = 100 or bTinhtrang = 1
 ---find by category---
 create proc sp_findbycategory(@idcategory int)
 as
 select p.* from tblSanPham as p, tblDanhMucSanPham as c where p.iMadanhmucsp = c.iMadanhmucsp and c.iMadanhmucsp = @idcategory 
---Người dùng---

create proc sp_select_nguoidung
as
select * from tblNguoiDung

create proc sp_insert_nguoidung
(
	@hoten nvarchar(100), @email nvarchar(200), @diachi nvarchar(300), @sdt varchar(15), @ngaysinh date, @matkhau varchar(50), @maquyen int
)
as
begin
	insert tblNguoiDung values(@hoten,@email,@diachi,@sdt,@ngaysinh,@matkhau,@maquyen)
end

exec sp_insert_nguoidung N'Nguyễn Thị Nhung', N'nhung2312@gmail.com', N'Số 15 Ngô Gia Tự, thành phố Bắc Giang','098123452','1997-02-09','12345',2
exec sp_insert_nguoidung N'Nguyễn Thị Hường', N'huong2912@gmail.com', N'Số 45 Ngô Gia Tự, thành phố Bắc Giang','098129452','1997-02-09','12345',2
exec sp_insert_nguoidung N'Hà Bảo Hân', N'han3209@gmail.com', N'Số 15 Ngô Quyền, thành phố Bắc Giang','098123852','1997-02-09','12345',2

create proc sp_update_nguoidung
(
	@manguoidung int, @hoten nvarchar(100), @email nvarchar(200), @diachi nvarchar(300), @sdt varchar(15), @ngaysinh date, @matkhau varchar(50), @maquyen int
)
as
update tblNguoiDung set sHoten = @hoten, sEmail = @email, sDiachi = @diachi, sSodienthoai = @sdt, dNgaysinh = @ngaysinh, sMatkhau = @matkhau, iMaquyen = @maquyen
where iManguoidung = @manguoidung

create proc sp_delete_nguoidung(@manguoidung int)
as
delete from tblNguoiDung where iManguoidung = @manguoidung

---Khách hàng ---

create proc sp_select_khachhang
as
select * from tblKhachHang

create proc sp_insert_khachhang
(
	@hoten nvarchar(100), @email nvarchar(200), @diachi nvarchar(300), @sdt varchar(15), @tendangnhap nvarchar(100), @matkhau varchar(50)
)
as
insert tblKhachHang values(@hoten,@email,@diachi,@sdt,@tendangnhap,@matkhau) 

exec sp_insert_khachhang N'Hà Lương Thắng', N'luongthang4321@gmail.com', N'809 Giải Phóng Hà Nội', '093422187', N'LuongThang', '12456'
exec sp_insert_khachhang N'Nguyễn Huy Hoàng', N'nguyenhuy4321@gmail.com', N'Quang Thịnh-Lạng Giang-Bắc Giang', '083422187', N'NguyenHoang', '12466'
exec sp_insert_khachhang N'Hà Lương Văn', N'havan4321@gmail.com', N'Quang Thịnh-Lạng Giang-Bắc Giang', '093422887', N'LuongVan', '12456'
exec sp_insert_khachhang N'Hà Thu Thùy', N'thuthuy@gmail.com', N'Hiệp Hòa Bắc Giang', '093422087', N'ThuThuy', '12456'
exec sp_insert_khachhang N'Lê Thị Dịu', N'diule@gmail.com', N'123 Ba Đình - Hà Nội', '093722187', N'LeDiu', '12456'
exec sp_insert_khachhang N'Nghiêm Hải Linh', N'nghiemlinh@gmail.com', N'Hương Sơn-Lạng Giang-Bắc Giang', '097422187', N'NghiemLinh', '12456'
exec sp_insert_khachhang N'Hà Nhất Linh', N'linhnhat@gmail.com', N'Thanh Lương-Lạng Giang-Bắc Giang', '093402187', N'NhatLinh', '12456'
exec sp_insert_khachhang N'Nguyễn Ngọc Nhi', N'nhingoc@gmail.com', N'89 Giải Phóng Hà Nội', '093492187', N'NgocNhi', '12456'

---Nhà cung cấp---

create proc sp_select_nhacungcap
as
select * from tblNhaCungCap

create proc sp_insert_nhacungcap
(
	@ten nvarchar(200), @sdt varchar(15), @email nvarchar(200), @diachi nvarchar(300)
)
as
insert tblNhaCungCap values(@ten,@sdt,@email,@diachi)

exec sp_insert_nhacungcap N'Công ty TNHH Tân Hoàng Nguyên','094328721',N'hoangnguyen128c@gmail.com',N'71 Phố Vọng, phường Đồng Tâm, quận Hai Bà Trưng, thành phố Hà Nội'
exec sp_insert_nhacungcap N'Công ty TNHH Thương Mại thiết bị điện Bắc Giang','099328721',N'thietbidien@gmail.com',N'62 đường Quang Trung, phường Trần Phú, thành phố Bắc Giang'

---Hình thức thanh toán---

insert tblHinhThucThanhToan values(N'Nhận hàng mới thanh toán')
insert tblHinhThucThanhToan values(N'Chuyển khoản')

---Trạng thái đơn hàng---

insert tblTrangThaiDonHang values(N'Chưa xác nhận')
insert tblTrangThaiDonHang values(N'Xác nhận')
insert tblTrangThaiDonHang values(N'Đang giao')
insert tblTrangThaiDonHang values(N'Hoàn thành')
insert tblTrangThaiDonHang values(N'Đã hủy')

---Đơn hàng---

create proc sp_select_donhang
as
select * from tblDonHang

alter proc sp_insert_donhang
(
	@thoigiandat datetime, @trangthaithanhtoan bit, @ghichu nvarchar(300), @manguoidung int, @makhachhang int, @mahttt int
)
as
insert tblDonHang values(@thoigiandat, @trangthaithanhtoan, @ghichu, @manguoidung, @makhachhang, @mahttt)

exec sp_insert_donhang '2021-01-04', 0, N'Gói cẩn thận giúp mình nhé', 1, 1, 1

---Chi tiết đơn hàng---

create proc sp_select_chitietdonhang
as
select * from tblChiTietDonHang

create proc sp_insert_chitietdonhang
(
	@madonhang int, @masp int, @soluong int
)
as
insert tblChiTietDonHang values(@madonhang,@masp,@soluong)

exec sp_insert_chitietdonhang 1,1,1

---Phiếu nhập---

create proc sp_select_phieunhap
as
select * from tblPhieuNhap

alter proc sp_insert_phieunhap
(
	@thoigianlap datetime, @ghichu nvarchar(300), @manguoidung int, @manhacungcap int
)
as
insert tblPhieuNhap values(@thoigianlap, @ghichu, @manguoidung, @manhacungcap)

exec sp_insert_phieunhap '2021-02-02', N'Giao đúng hàng giúp tôi nhé', 1,1

---Chi tiết phiếu nhập---

create proc sp_select_chitietphieunhap
as
select * from tblChiTietPhieuNhap

create proc sp_insert_chitietphieunhap
(
	@maphieunhap int, @masp int, @soluong int, @dongia float
)
as
insert tblChiTietPhieuNhap values(@maphieunhap, @masp, @soluong, @dongia)

exec sp_insert_chitietphieunhap 1,1,5,200

update tblDanhMucSanPham set sTendanhmucsp = N'Dây điện abc', bTinhtrangban = 0 where iMadanhmucsp = 1

-- update sanpham---
update tblSanPham set sMasanpham = '1232', sTensanpham = N'Dây điện nhôm', fGiaban = 123, sAnhsanpham = N'dskjfdkf.jpg',
bTinhtrang = 0, sMota = N'fdgfg', iSoluong = 2, sThongsokythuat = N'fdfg', iBaohanh = 4, sDonvitinh = N'cái', iMadanhmucsp = 6 where iId_sanpham = 10

--search san pham---
select * from tblSanPham where sTensanpham like N'%Dây điện%'

---select đơn hàng---
select tblDonHang.iMadonhang as N'Mã đơn hàng', tblDonHang.dThoigiandat as N'Thời gian đặt', tblTrangThaiDonHang.sTentrangthai as N'Trạng thái đơn hàng', tblHinhThucThanhToan.sTenhinhthucthanhtoan as N'Hình thức thanh toán',
tblDonHang.bTrangthaithanhtoan as N'Trạng thái thanh toán' from ((tblDonHang inner join tblTrangThaiDonHang on tblDonHang.iMatrangthai = tblTrangThaiDonHang.iMatrangthai)
inner join tblHinhThucThanhToan on tblDonHang.iMahinhthucthanhtoan = tblHinhThucThanhToan.iMahinhthucthanhtoan)
