import requests
from bs4 import BeautifulSoup
import pandas as pd

"""
검색어 : 지진
(최신순 / 2011.01.01 ~ 2022.03.14 / 연합뉴스)
수집 기사 수 : 1000개 (10개 x 100페이지)
"""

"""
페이지 url 100개를 page_urls에 저장
"""
page_former = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%A7%80%EC%A7%84&sort=1&photo=0&field=0&pd=3&ds=2011.01.01&de=2022.03.14&mynews=1&office_type=1&office_section_code=2&news_office_checked=1001&nso=so:dd,p:from20110101to20220314,a:all&start="

page_urls = []

for page in range(1, 1000, 10):
    page_urls.append(page_former + str(page))

"""
각 url별로 기사들의 기사 / 날짜 / 언론사 / url / 본문 일부를 저장함.
"""

import time

urls = []
titles = []
sources = []
dates = []
texts = []

for idx, page_url in enumerate(page_urls):
    try:
        request = requests.get(page_url)
        soup = BeautifulSoup(request.text, "html.parser")
        time.sleep(1)

        a = soup.select("a.news_tit")
        title = [ai["title"] for ai in a]
        url = [ai["href"] for ai in a]

        b = soup.select("a.info.press")
        source = [bi.text.replace("선정", "").replace("언론사", "").strip() for bi in b]

        c = soup.select("span.info")
        date = [ci.text for ci in c if "면" not in ci.text]

        # n일 전 / n시간 전 => 날짜 형식으로 변환하기
        for idx, cnt_date in enumerate(date):
            if cnt_date[-1] == '전':
                cnt_url = url[idx]
                year = cnt_url[17:21]
                month = cnt_url[21:23]
                day = cnt_url[23:25]

                date[idx] = f"{year}.{month}.{day}."

        d = soup.select("a.api_txt_lines.dsc_txt_wrap")
        text = []

        for di in d:
            try:
                text.append(di.text)
            except:
                text.append("")

        urls.append(url)
        titles.append(title)
        sources.append(source)
        dates.append(date)
        texts.append(text)
    except:
        urls.append([])
        titles.append([])
        sources.append([])
        dates.append([])
        texts.append([])

"""
1000개 기사 데이터를 모아 csv 파일로 저장함.
"""
urls_all = sum(urls, [])
titles_all = sum(titles, [])
sources_all = sum(sources, [])
dates_all = sum(dates, [])
texts_all = sum(texts, [])
category = ["지진"] * 1000

result = pd.DataFrame({
    "재난_분류": category,
    "제목": titles_all,
    "날짜": dates_all,
    "URL": urls_all,
    "본문_일부": texts_all,
    "언론사": sources_all,
})

"""
본문 전체 추가
"""
from newspaper import Article
import re

stories = []
for url in urls_all:
    try:
        news = Article(url, language='ko')
        news.download()
        time.sleep(0.1)
        news.parse()

        text = news.text
        # email 제거
        pattern = '([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+)'
        text = re.sub(pattern=pattern, repl='', string=text)
        # URL 제거
        pattern = '(http|https)://(?:[-\w.]|(?:%[\da-fA-F]{2}))+'
        text = re.sub(pattern=pattern, repl='', string=text)
        # (), <>, [] 안의 글자 제거
        pattern = r'\([^)]*\)'
        text = re.sub(pattern=pattern, repl='', string=text)
        pattern = r'\<[^)]*\>'
        text = re.sub(pattern=pattern, repl='', string=text)
        pattern = r'\[[^)]*\]'
        text = re.sub(pattern=pattern, repl='', string=text)
        # OOO 기자, OOO 특파원 제거
        pattern = r'\[기 - 힣][기 - 힣][기 - 힣] 기자'
        text = re.sub(pattern=pattern, repl='', string=text)
        pattern = r'\[기 - 힣][기 - 힣][기 - 힣]기자'
        text = re.sub(pattern=pattern, repl='', string=text)
        pattern = r'\[기 - 힣][기 - 힣][기 - 힣]특파원'
        text = re.sub(pattern=pattern, repl='', string=text)
        pattern = r'\[기 - 힣][기 - 힣][기 - 힣] 특파원'
        text = re.sub(pattern=pattern, repl='', string=text)
        """
        아래의 단어들 제거
        
        카카오톡에 / 트위터에 / 카카오 스토리에 / 페이스북 메신저에 / 페이스북에 / 핀터레스트에 / 네이버 밴드에 / 네이버 블로그에
        닫기 / 공유 / 광고 / 제보는 / 카카오톡 / okjebo / 제공 / 단독 / 더보기 / 기자 / 특파원 / 기상청 / 속보 / 종합 / 한국시간
        """
        pattern_words = [
            '카카오톡에', '트위터에', '카카오 스토리에', '페이스북 메신저에', '페이스북에',
            '핀터레스트에', '네이버 밴드에', '네이버 블로그에', '닫기', '(한국시간)', '(종합)',
            '공유', '광고', '제보는', '카카오톡', 'okjebo', '제공', '단독', '더보기', '기자', '특파원', '기상청', '속보',
        ]

        for p_word in pattern_words:
            text = text.replace(p_word, "")

        stories.append(text)
    except:
        stories.append("")

result["본문_전체"] = stories

# csv 파일로 추출하기
result.to_csv('C:/Users/SSAFY/Desktop/SSAFY/특화 프로젝트/S06P21A403/0311/earthquake_result_0316_1000.csv')


"""
키워드 추출 내용 txt 파일로 추출
"""
from konlpy.tag import Okt
okt = Okt()

write_file = open('0316_article_results.txt', mode='w')

for i in range(len(result)):
    nouns = okt.nouns(result["본문_전체"].loc[i])
    # 1글자는 제거
    nouns = [x for x in nouns if len(x) > 1]

    if nouns:
        for noun in nouns:
            write_file.write(noun + " ")

        write_file.write('\n')

write_file.close()
