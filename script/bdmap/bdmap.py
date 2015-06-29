
import time
import urllib.parse
import http.client 
import json

ak='nsNo6385eG8L0jfcaFPVThXX'

def printHeaderInfo():
    print("==== ==== ==== ====> 开始从百度抓取门店数据","@",time.strftime('%H:%M:%S',time.localtime(time.time())))

def etl():
    print("==== ==== ==== ====> 开始从百度抓取门店数据","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    query = '便利店'
    scope = 1
    pageSize = 5
    pageNum = 0
    region = '深圳市'
    resp = fetch(query, scope, pageSize, pageNum, region)
    print( resp )
    transform(resp['results'])
    

def fetch(query, scope, pageSize, pageNum, region):
    print("==== ==== ==== ====> 开始搜索数据@%s, 检索关键字:%s, 详细程度: %s, 记录数: %d, 分页页码: %d, 检索区域: %s" % ( time.strftime('%H:%M:%S',time.localtime(time.time())) , query, scope, pageSize, pageNum, region ))

    params = {'ak': ak, 'output': 'json', 'query': query, 'scope': scope, 'page_size': pageSize, 'page_num': pageNum, 'region': region }
    params = "/place/v2/search?%s" % (urllib.parse.urlencode(params))

    data = False

    conn = http.client.HTTPConnection("api.map.baidu.com")
    conn.request("GET", params)
    resp = conn.getresponse()
    if( resp.status == 200 ):
        data = resp.read().decode('utf-8')
        data = json.loads( data )
    else:
        print (resp.status, " - ", resp.reason)

    #print( data )
    return data

def transform( bdStores ):
    print("==== ==== ==== ====> 开始解析数据","@",time.strftime('%H:%M:%S',time.localtime(time.time())))
    for bdStore in bdStores:
        print( bdStore['name'] )

if __name__ == '__main__':
    printHeaderInfo()
    etl()
