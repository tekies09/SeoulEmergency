from konlpy.tag import Okt

okt = Okt()

def parseNewsPhrases() :
	read_file = open('../data/test-news-1.txt', mode='r')
	write_file = open('../data/test-news-1-phrases.txt', mode='w')
	
	lines =  read_file.readlines()
	
	for line in lines :
		# print(okt.phrases(line), end='')
		if (line == '\n') :
			continue
		
		parsed_phrases = okt.phrases(line)
		
		for phrase in parsed_phrases :
			write_file.write(phrase + ",")
			
		write_file.write('\n')
		
	read_file.close()
	write_file.close()
	
def parseNewsNouns() :
	read_file = open('../data/test-news-1.txt', mode='r')
	write_file = open('../data/test-news-1-nouns.txt', mode='w')
	
	lines =  read_file.readlines()
	
	for line in lines :
		# print(okt.phrases(line), end='')
		if (line == '\n') :
			continue
		
		parsed_nouns = okt.nouns(line)
		
		for noun in parsed_nouns :
			write_file.write(noun + ",")
			
		write_file.write('\n')
		
	read_file.close()
	write_file.close()
	
parseNewsPhrases()
parseNewsNouns()
