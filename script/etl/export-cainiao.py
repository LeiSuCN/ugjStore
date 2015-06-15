import time
import openpyxl
import mysql.connector

month = 201503

print("==== ==== ==== =====> 开始创建excel","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
wb = openpyxl.Workbook()
ws = wb.active
ws.title = '统计'

ws.append(['编号', '数量', '店名'])

rowidx = 1
print("==== ==== ==== =====> 开始查询统计表","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
stmt = (" select"
        "    store_id, store_name, amount"
        " from"
        "    statistics_order_month_sumary_store"
        " where"
        "    time = %d"
        " order by"
        "    store_id asc"%(month))
cnx = mysql.connector.connect(user='ugj', password='ugjuser', host='localhost', database='ugj')
cursor = cnx.cursor()
cursor.execute(stmt)
for (code, name, count) in cursor:
    ws.append([code, count, name])
    rowidx = rowidx + 1
    
cursor.close()
cnx.close()
print("==== ==== ==== =====> 查询统计表完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))


wb.save('cainiaoyz-%d.xlsx'%(month))
print("==== ==== ==== =====> 创建excel完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    
