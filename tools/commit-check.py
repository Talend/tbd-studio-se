import os

semantic_commits_regex = "(?:chore|docs|feat|fix|refactor|style|test)[\(\[](?:TBD|TDI|TUP|DEVOPS)-(?:\d{4})[)\]]:.*"


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


if __name__ == '__main__':
    check_commit_message()
