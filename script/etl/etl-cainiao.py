import sys
import re
import time
import xlrd
import mysql.connector

def etl(file):

    # 打开excel文件
    print("==== ==== ==== =====> 开始打开数据文件","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    book = xlrd.open_workbook(file)
    print("==== ==== ==== =====> 打开数据文件完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

    # 解析excel文件
    print("==== ==== ==== =====> 开始解析汇总Sheet","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    '''
    0  月份
    1  渠道
    2  公司名称
    3  单价
    4  单量
    5  金额
    6  备注
    '''
    sumarySheet = book.sheet_by_index(0)

    month = 201500 + int(sumarySheet.cell_value(1,0).replace('月',''))

    total = int(sumarySheet.cell_value(1,4))
    print("==== ==== ==== =====> 解析汇总Sheet完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

    print("==== ==== ==== =====> 开始解析明细Sheet","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    '''
    0  服务站订单编码
    1  分表字段_服务站id
    2  服务站名称
    3  运单号
    4  物流公司id
    5  记录创建时间
    6  订单来源
    7  货物到站时间
    8  货物提货时
    9  间
    10 交易订单id
    11 服务商名称
    12 金额
    13 备注
    '''
    detailSheet = book.sheet_by_index(1)

    orderStatistics = {}

    assert total + 1 == detailSheet.nrows, '单量与明细中的数量不相等'

    # 数据库表original_data_cainiao
    rows = []

    # 服务站名称格式为：XXXX（nnnn）,XXXX为服务站名称, nnnn为菜鸟编号
    # pattern4code = re.compile(r'^.*[（\\(](\d+)[）\\)].*$')
    pattern4code = re.compile(r'^.*(\d\d\d\d).*$')
    for rx in range(1, detailSheet.nrows):

        #print("%d/%d......"%(rx,total))
    
        m = pattern4code.match(detailSheet.cell_value(rx, 2))
        if( m ):
            code = m.group(m.lastindex)

            if not orderStatistics.get(code):
                orderStatistics[code] = {}
                orderStatistics[code]['name'] = detailSheet.cell_value(rx, 2)
                orderStatistics[code]['count'] = 0

            orderStatistics[code]['count'] = orderStatistics[code]['count'] + 1

            store_order_no = detailSheet.cell_value(rx, 0)
            store_code = detailSheet.cell_value(rx, 1)
            store_name = detailSheet.cell_value(rx, 2)
            order_number = detailSheet.cell_value(rx, 3)
            logistics_company_id = detailSheet.cell_value(rx, 4)
            order_create_time = detailSheet.cell_value(rx, 5)
            order_source = detailSheet.cell_value(rx, 6)
            order_arrive_time = detailSheet.cell_value(rx, 7)

            order_take_time_date = xlrd.xldate_as_tuple( detailSheet.cell_value(rx, 8), 0 )
            order_take_time_time = xlrd.xldate_as_tuple( detailSheet.cell_value(rx, 9), 0 )
        
            order_take_time = '%d-%d-%d %d:%d:%d'%(order_take_time_date[0],order_take_time_date[1],order_take_time_date[2]
                                               ,order_take_time_time[3],order_take_time_time[4],order_take_time_time[5])

            order_id = detailSheet.cell_value(rx, 10)
            servicename = detailSheet.cell_value(rx, 11)
            sum_ = detailSheet.cell_value(rx, 12)
            comment = detailSheet.cell_value(rx, 13)

            rows.append( ( month, code, store_order_no, store_code, store_name, order_number, logistics_company_id, order_create_time, order_source, order_arrive_time, order_take_time, order_id, servicename, sum_, comment) )
        else:
            print("格式错误:%s"%(cellVal)) # TODO(error)

    # 开启数据库连接
    cnx = mysql.connector.connect(user='ugj', password='ugjuser', host='localhost', database='ugj')

    print("==== ==== ==== =====> 开始插入明细表","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    stmt = "INSERT INTO original_data_cainiao (time, code, store_order_no, store_code, store_name, order_number, logistics_company_id, order_create_time,order_source, order_arrive_time, order_take_time, order_id, servicename, sum, comment) VALUES (%s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
   
    try:
        cursor = cnx.cursor()
        cursor.executemany(stmt, rows)
        #cnx.commit()
        cursor.close()
    except Exception as e:
        cnx.rollback()
        print(e)
        print("!!!! ==== ==== =====> 插入明细表异常","@",time.strftime('%H:%M:%S',time.localtime(time.time())))    
    
    
    print("==== ==== ==== =====> 插入明细表完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

    print("==== ==== ==== =====> 解析明细Sheet完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

    print("==== ==== ==== =====> 开始插入统计表","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    rows = []
    for kv in orderStatistics.items():
        rows.append( ( kv[0], 4, month, kv[1]['name'], kv[1]['count'], kv[1]['count']) )

    stmt = "INSERT INTO statistics_order_month_sumary_store (store_id, type, time, store_name, amount, total) VALUES (%s, %s, %s, %s, %s, %s)"
    
    try:
        cursor = cnx.cursor()
        cursor.executemany(stmt, rows)   
        cursor.close()
        cnx.commit()
    except Exception as e:
        cnx.rollback()
        print(e)
        print("!!!! ==== ==== =====> 插入统计表异常","@",time.strftime('%H:%M:%S',time.localtime(time.time())))    
    print("==== ==== ==== =====> 插入统计表完成","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

    # 关闭数据库连接
    cnx.close()

def main(argv):

    # file = argv[1]

    # etl(file)
    
    etl("data.xlsx")

if __name__ == '__main__':
    main(sys.argv)
