from django.db import models

class Students(models.Model):
    cID = models.AutoField(primary_key=True)
    cName = models.CharField(max_length=28, blank=False)
    cSex = models.CharField(max_length=1, blank=False, default='M')
    cBirthday = models.DateField(blank=False) #手動，要填寫日期

    # 建立時會自動填入當前日期，欄位值修正時，不會更新
    # cBirthday = models.DateField(blank=False，auto_now_add=True) 

    # 建立時會自動填入當前日期，欄位值修正時，會更新
    # cBirthday = models.DateField(blank=False，auto_now=True) 
    cEmail = models.CharField(max_length=100, blank=False)
    cPhone = models.CharField(max_length=50, blank=False)
    cAddr = models.CharField(max_length=255, blank=False)
    cHeight = models.IntegerField(blank=True)
    cWeight = models.IntegerField(blank=True)