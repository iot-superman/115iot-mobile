# 關聯資料
    # one to one
    # add(兩個表資料一起新增)
    # 先檢查是否已經存在王大明的紀錄 
    # student_exists = Student.objects.filter(cName='王大明1').exists() 
    # if not student_exists: 
    #     # 若不存在則新增資料 
    #     datas = Student.objects.create(cName='王大明1', cSex='M', cBirthday='2020-08-20', cEmail='wang@yahoo.com.tw', 
    #                                    cPhone='09333333', cAddr='新竹', cHeight=120, cWeight=40) 
    #     Permissions.objects.create(cID=datas, passwd='0000', level='0') 
    #     print("新資料已新增。") 
    # else: 
    #     print("資料庫中已存在王大明的紀錄。")

    # add(先新增Student資料，再新增Permissions資料)
    # 檢查是否已經存在王大明的紀錄
    # 檢查學生資料表中是否存在名為Bill的學生

    # student_exists = Student.objects.filter(cName='Bill3').exists()
    # if not student_exists:
    #     # 若不存在則新增資料
    #     Student.objects.create(
    #     cName="bill",
    #     cSex="M",
    #     cBirthday="2021-08-08",
    #     cEmail="bill@yahoo.com.tw",
    #     cPhone="0922222",
    #     cAddr="新竹",
    #     cHeight=150,
    #     cWeight=70
    #     )
    #     print("新資料已新增。")
    # else:
    #     print("資料庫中已存在，未新增新資料。")

    # try:
    #     student = Student.objects.get(cName='bill3')
    #     # 檢查Permissions表中是否已經有相應的紀錄
    #     permission_exists = Permissions.objects.filter(cID=student).exists()

    #     if not permission_exists:
    #         # 若Permissions表中無相應紀錄，則新增
    #         Permissions.objects.create(cID=student, passwd='0000', level='0')
    #         print("已新增Permissions紀錄。")
    #     else:
    #         print("Permissions表中已存在對應的紀錄，未新增。")
    # except Student.DoesNotExist:
    #     print("資料庫中無名為Bill的學生紀錄，未新增Permissions紀錄。")


    # search
    # datas = Permissions.objects.values('cID__cID', 'cID__cName', 'passwd', 'level')
    # datas = Permissions.objects.values('cID', 'cID__cName', 'passwd', 'level')
    # cID__cName:透過雙底線（__）語法，可以存取外鍵模型（這裡是 cID 所指的模型）裡的欄位
    
    # for data in datas:
    #     print(data)

    # update
    # try:
    #     #找到對應的 Student 資料
    #     Student_data = Student.objects.get(cName='王大明')
    #     #找到對應的 Permissions 資料
    #     permission_data = Permissions.objects.get(cID=Student_data)
    #     #更新密碼
    #     permission_data.passwd = '1234'
    #     permission_data.save()
    #     print("Password updated successfully.")
    # except Student.DoesNotExist:
    #     print("Student '王大明' not found.")
    # except Permissions.DoesNotExist:
    #     print("Permissions for the student not found.")
    ##################
    # update2
    # try:
    #     # 確認有 '王大明'
    #     student_data = Student.objects.get(cName='王大明')
    #     # 更新密碼
    #     Permissions.objects.filter(cID=student_data).update(passwd='新的密碼')
    #     print("Password for '王大明' updated successfully.")
    # except:
    #     print("Student '王大明' not found.")
    ##################
    # delete (兩個資料表一起刪除)
    # try:
    #     # 確認有 '王大明'
    #     student_data = Student.objects.get(cName='bill3')
    #     # 刪除 '王大明'
    #     student_data.delete()
    #     print("deleted successfully.")
    # except:
    #     print("not found.")

    # delete (Student 為主資料，只刪除其對應的 Permissions)
    # try:
    #     # 確認有 '王大明'
    #     student_data = Student.objects.get(cName='王大明1')
    #     # 刪除 '王大明' 的帳號
    #     Permissions.objects.filter(cID=student_data).delete()
    #     print("deleted successfully.")
    # except:
    #     print("not found.")

    ###############################################
    # one to many
    # add
    # 若學生未建立，一起建立學生與成績
    # try:
    #     # 確認無此人
    #     student_data = Student.objects.get(cName='王大明')
    #     print("already exists.")
    # except:
    #     # 創建新學生資料
    #     student_data = Student.objects.create(
    #         cName='王大明', cSex='M', cBirthday='2020-08-20',
    #         cEmail='wang@yahoo.com.tw', cPhone='09333333', cAddr='新竹',
    #         cHeight=120, cWeight=40
    #     )
    #     print("created successfully.")
        
    #     # 創建成績資料
    #     Scorelist.objects.create(cID=student_data, course='地理', score=60)
    #     Scorelist.objects.create(cID=student_data, course='音樂', score=100)
    #     print("created successfully.")

    # 若學生已建立，再建立科目成績
    # try:
    #     # 確認有 '王大明'
    #     student_data = Student.objects.get(cName='王大明')
    #     # 檢查是否已經有 '地理' 科目
    #     geography_exists = Scorelist.objects.filter(cID=student_data, course='地理').exists()
    #     if not geography_exists:
    #         Scorelist.objects.create(cID=student_data, course='地理', score=60)
    #         print("Geography score added successfully.")
    #     else:
    #         print("already has a score for 'Geography'.")
        
    #     # 檢查是否已經有 '音樂' 科目
    #     music_exists = Scorelist.objects.filter(cID=student_data, course='音樂').exists()
    #     if not music_exists:
    #         Scorelist.objects.create(cID=student_data, course='音樂', score=100)
    #         print("Music score added successfully.")
    #     else:
    #         print("already has a score for 'Music'.")
        
    # except:
    #     print("not found.")

    ##################
    # search
    # 外連鍵的scorelist中s小寫
    # datas = Student.objects.filter(scorelist__course='國文').values(
    #     'cID', 'cName', 'scorelist__course', 'scorelist__score')
    # for data in datas:
    #     print(data)

    # from django.db.models import Sum,Count,Max,Min,Avg
    # datas =  Student.objects.values_list('cID','cName').annotate(Sum('scorelist__score'), Avg('scorelist__score'))
    # for data in datas:
    #     print(data)
    ##################
    # update
    # try:
    #     # 確認是否存在指定名稱的學生
    #     student_data = Student.objects.get(cName='簡奉君')
    #     # 確認是否存在指定的課程並更新分數
    #     course_exists = Scorelist.objects.filter(cID=student_data, course='國文').exists()

    #     if course_exists:
    #         Scorelist.objects.filter(cID=student_data, course='國文').update(score=0)
    #         print("分數更新成功。")
    #     else:
    #         print("未找到該課程，無法更新分數。")
    # except:
    #     print("未找到該學生。")
    ##################
    # delete
    # 刪除王大明資料與科目所有成績
    # try:
    #     Student.objects.get(cName='王燕博').delete()
    #     print("刪除資料成功。")
    # except:
    #     print("未找到該學生。")

    # 刪除王大明的某科成績
    # try:
    #     student_data = Student.objects.get(cName='林心儀')
    # #     # 確認是否存在指定的課程並刪除該課程成績
    #     course_exists = Scorelist.objects.filter(cID=student_data, course='國文').exists()
    #     if course_exists:
    #         Scorelist.objects.filter(cID=student_data, course='國文').delete()
    #         print("成績刪除成功。")
    #     else:
    #         print("未找到該課程成績，無法刪除。")
    # except:
    #     print("未找到該學生。")

    ###############################################
    # many to many
    # add
    # 檢查 Author
    #    │
    #    ├─存在 → 取得 Author
    #    └─不存在 → 新增 Author

    # 檢查 Book
    #    │
    #    ├─存在 → 取得 Book
    #    └─不存在 → 新增 Book

    # 檢查 Book 是否已有該 Author
    #    │
    #    ├─有 → 不動作
    #    └─沒有 → add(author)

    # if Author.objects.filter(aID='b100001').exists():
    #     author = Author.objects.get(aID='b100001')
    #     print("作者已存在")
    # else:
    #     author = Author.objects.create(aID='b100001', name='John')
    #     print("作者已新增")


    # # 檢查書籍是否存在
    # if Book.objects.filter(isbn='97871').exists():
    #     book = Book.objects.get(isbn='97871')
    #     print("書籍已存在")
    # else:
    #     book = Book.objects.create(isbn='97871', name='HTML')
    #     print("書籍已新增")


    # # 檢查作者與書籍是否已關聯
    
    # if book.authors.filter(aID='b100001').exists():
    #     print("作者與書籍已經關聯")
    # else:
    #     book.authors.add(author)
    #     print("作者與書籍已新增關聯")
    ##################
    # search
    # 某書有哪些作者
    # datas = Book.objects.filter(isbn="978711").values('name', 'authors__name')

    # if datas.exists():
    #     for data in datas:
    #         print(data)
    # else:
    #     print("No data found")  
        
    # # 某作者有哪些書
    # try:
    #     author = Author.objects.get(aID="a10002001")

    #     for book in author.book_set.all():
    #         print(book.name)
    # except Author.DoesNotExist:
    #     print("No data found")
    
    ##################
    # upate
    # new_name="小明2"
    # aID = "b100001"
    # try:
    #     # 检查作者是否存在
    #     author = Author.objects.get(aID=aID)
    #     author.name = new_name  # 修改名字
    #     author.save()
    #     print(f"Author name updated to '{new_name}'.")
    # except Author.DoesNotExist:
    #     print(f"No author found with aID '{aID}'.")

    # new_name="C++2"
    # isbn = "978711"
    # try:
    #     # 检查书籍是否存在
    #     book = Book.objects.get(isbn=isbn)
    #     book.name = new_name  # 修改书名
    #     book.save()
    #     print(f"Book name updated to '{new_name}'.")
    # except Book.DoesNotExist:
    #     print(f"No book found with ISBN '{isbn}'.")


    # ##################
    # # delete
    # # 刪除某書的作者，Author內作者仍保留
    # try:
    #     book = Book.objects.get(isbn="97871")
    #     book.authors.clear()
    #     print("作者已被移除")
    # except Book.DoesNotExist:
    #     print("沒有找到該書籍")

    # # 刪除某書，Author內作者仍保留
    # try:
    #     book = Book.objects.get(isbn='97871')
    #     book.delete()
    #     print('書籍已刪除')
    # except Book.DoesNotExist:
    #     print('沒有找到ISBN的書籍')

    # # 刪除作者，book內書仍保留
    # try:
    #     author = Author.objects.get(aID='a10002002')
    #     author.delete()
    #     print('作者已被删除，但書籍保留')
    # except Author.DoesNotExist:
    #     print('沒有找到 aID')
    ###############################################