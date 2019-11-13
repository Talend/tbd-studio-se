# coding=utf8
import os
import re

team_tickets = "TBD|TDI|TUP|DEVOPS"
semantic_prefixs = "chore|docs|feat|fix|refactor|style|test"

tbd_commit_regex = "(%s)[\(\[\:]((?:%s)-(?:\d{3,7}))(?:[)\]]:|:)(.*)" % (semantic_prefixs, team_tickets)
# this regex will extract some information based on the accepted convention:
# group1 : semantic, group2: ticket ID, group3: message
# Some samples:
# feat(TBD-901): msg1       ==> group1: feat,     group2: TBD-901,   group3: msg1
# chore[TBD-9011]: msg2     ==> group1: chore,    group2: TBD-9011,  group3: msg2
# refactor:TBD-99011: msg3  ==> group1: refactor, group2: TBD-99011, group3: msg3

COLOR_GREEN = '\033[1;32m'
COLOR_YELLOW = '\033[1;33m'
COLOR_RED = '\033[1;31m'
END_COLOR = '\033[1;m'


def red(text):
    return '%s%s%s' % (COLOR_RED, str(text), END_COLOR)


def yellow(text):
    return '%s%s%s' % (COLOR_YELLOW, str(text), END_COLOR)


def green(text):
    return '%s%s%s' % (COLOR_GREEN, str(text), END_COLOR)


def check_commit_message():
    print('CHANGE_TITLE=%s' % os.environ['CHANGE_TITLE'])
    print('CHANGE_URL=%s' % os.environ['CHANGE_URL'])
    # print('CHANGE_AUTHOR=%s' % os.environ['CHANGE_AUTHOR'])
    # print('CHANGE_AUTHOR_DISPLAY_NAME=%s' % os.environ['CHANGE_AUTHOR_DISPLAY_NAME'])
    # print('CHANGE_AUTHOR_EMAIL=%s' % os.environ['CHANGE_AUTHOR_EMAIL'])
    # print('CHANGE_BRANCH=%s' % os.environ['CHANGE_BRANCH'])
    # print('CHANGE_TARGET=%s' % os.environ['CHANGE_TARGET'])
    # print('CHANGE_ID=%s' % os.environ['CHANGE_ID'])
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
        print("")
        print("%s: the message does not follow convention. '%s'" % (red('ERROR'), red(os.environ['CHANGE_TITLE'])))
        exit(-1)
    else:
        (start, end) = result.span()
        if start != 0:
            print("%s: unexpected prefix '%s'" % (red('ERROR'), red(result.string[0:start])))
        else:
            semantic = result.group(1)
            ticket_id = result.group(2)
            message = result.group(3)
            print("%s: commit message is valid for %s:\n\tsemantic : %s\n\tticket   : %s\n\tmessage  : %s"
                  % (green('INFO'),
                     yellow(os.environ['BRANCH_NAME']),
                     green(semantic),
                     green(ticket_id),
                     green(message.strip())))


if __name__ == '__main__':
    check_commit_message()
