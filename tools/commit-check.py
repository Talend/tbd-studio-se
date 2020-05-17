# coding=utf8
import os
import re

team_tickets = "TBD|TDI|TUP|DEVOPS|TPS"
semantic_prefixes = "chore|docs|feat|fix|refactor|style|test"

tbd_commit_regex = "(%s)[\(\[\:]((?:%s)-(?:\d{3,7}))(?:[)\]]:|:)(.*)" % (semantic_prefixes, team_tickets)
# this regex will extract some information based on the accepted convention:
# group1 : semantic, group2: ticket ID, group3: message
# Some samples are listed in case of errors


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


def print_sample(commit, feat, ticket, message):
    print('%-40s ==> semantic: %-20s, ticket: %-23s, description: %s' % (
        green(commit),
        green(feat),
        green(ticket),
        green(message)
    ))


def check_commit_message():
    # print('CHANGE_TITLE=%s' % os.environ['CHANGE_TITLE'])
    # print('CHANGE_URL=%s' % os.environ['CHANGE_URL'])
    # print('CHANGE_AUTHOR=%s' % os.environ['CHANGE_AUTHOR'])
    # print('CHANGE_AUTHOR_DISPLAY_NAME=%s' % os.environ['CHANGE_AUTHOR_DISPLAY_NAME'])
    # print('CHANGE_AUTHOR_EMAIL=%s' % os.environ['CHANGE_AUTHOR_EMAIL'])
    # print('CHANGE_BRANCH=%s' % os.environ['CHANGE_BRANCH'])
    # print('CHANGE_TARGET=%s' % os.environ['CHANGE_TARGET'])
    # print('CHANGE_ID=%s' % os.environ['CHANGE_ID'])
    # print('BRANCH_NAME=%s' % os.environ['BRANCH_NAME'])
    # if 'CHANGE_FORK' in os.environ:
    #     print("CHANGE_FORK=%s" % os.environ['CHANGE_FORK'])
    # if 'TAG_NAME' in os.environ:
    #     print(os.environ['TAG_NAME'])
    #     print(os.environ['TAG_TIMESTAMP'])
    #     print(os.environ['TAG_UNIXTIME'])
    #     print(os.environ['TAG_UNIXTIME'])

    ## PR-MERGE BUILD : we check the PR title to ensure the commit will follow convention
    if 'CHANGE_TITLE' in os.environ:
        result = re.search(tbd_commit_regex, os.environ['CHANGE_TITLE'])
        if result is None:
            print('')
            print("%s: the message does not follow convention. '%s'" % (red('ERROR'), red(os.environ['CHANGE_TITLE'])))
            print('')
            print('check tickets prefix is in [%s]' % yellow(team_tickets))
            print('check semantic prefix is in [%s]' % yellow(semantic_prefixes))
            print('\nsome samples:')
            print_sample('feat(TBD-007): msg1', 'feat', 'TBD-007', 'msg1')
            print_sample('chore[TUP-1984]: msg2', 'chore', 'TUP-1984', 'msg2')
            print_sample('refactor:TDI-199999: msg3', 'refactor', 'TDI-199999', 'msg3')
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
                         yellow(os.environ['BRANCH_NAME'] if 'BRANCH_NAME' in os.environ else 'unknown branch'),
                         green(semantic),
                         green(ticket_id),
                         green(message.strip())))


if __name__ == '__main__':
    check_commit_message()
