import os
import re

team_tickets = "TBD|TDI|TUP|DEVOPS"
semantic_prefixs = "chore|docs|feat|fix|refactor|style|test"

tbd_commit_regex = "(%s)[\(\[\:]((?:%s)-(?:\d{3,7}))(?:[)\]]:|:)(.*)" % (semantic_prefixs, team_tickets)
# this regex will extract some information based on the accepted convention
# feat(TBD-901): tata       ==> group1: feat,     group2: TBD-901,   group3: tata
# chore[TBD-9011]: titi     ==> group1: chore,    group2: TBD-9011,  group3: titi
# refactor:TBD-99011: toto  ==> group1: refactor, group2: TBD-99011, group3: toto

COLOR_RED = '\033[1;31m'
END_COLOR = '\033[1;m'


def red(text):
    return '%s%s%s' % (COLOR_RED, str(text), END_COLOR)


def check_commit_message():
    print('CHANGE_TITLE=%s' % os.environ['CHANGE_TITLE'])
    print('CHANGE_URL=%s' % os.environ['CHANGE_URL'])
    print('CHANGE_AUTHOR=%s' % os.environ['CHANGE_AUTHOR'])
    print('CHANGE_AUTHOR_DISPLAY_NAME=%s' % os.environ['CHANGE_AUTHOR_DISPLAY_NAME'])
    print('CHANGE_AUTHOR_EMAIL=%s' % os.environ['CHANGE_AUTHOR_EMAIL'])
    print('CHANGE_BRANCH=%s' % os.environ['CHANGE_BRANCH'])
    print('CHANGE_TARGET=%s' % os.environ['CHANGE_TARGET'])
    print('CHANGE_ID=%s' % os.environ['CHANGE_ID'])
    print('BRANCH_NAME=%s' % os.environ['BRANCH_NAME'])
    if 'CHANGE_FORK' in os.environ:
        print("CHANGE_FORK=%s" % os.environ['CHANGE_FORK'])
    if 'TAG_NAME' in os.environ:
        print(os.environ['TAG_NAME'])
        print(os.environ['TAG_TIMESTAMP'])
        print(os.environ['TAG_UNIXTIME'])
        print(os.environ['TAG_UNIXTIME'])
    result = re.search(tbd_commit_regex, os.environ['CHANGE_TITLE'])
    if result is None:
        print("ERROR : the message does not follow convention. %s" % red(os.environ['CHANGE_TITLE']))
        print("")
    (start, end) = result.span()
    if start != 0:
        print("ERROR : unexpected prefix %s" % red(result[0:start]))


if __name__ == '__main__':
    check_commit_message()
