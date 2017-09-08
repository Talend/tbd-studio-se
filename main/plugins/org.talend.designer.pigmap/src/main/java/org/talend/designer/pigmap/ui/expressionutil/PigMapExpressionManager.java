// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.ui.expressionutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Substitution;
import org.apache.oro.text.regex.Util;
import org.talend.commons.utils.data.text.StringHelper;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapExpressionManager {

    private final static String EXPRESSION_PATTERN = "(\\[)\\s*(\\w+)\\s*\\.(\\w+\\s*:\\s*(/.+?)+(/@.+?)*)\\s*(\\])|((?!\\[)\\s*\\w+)\\s*\\.\\s*(\\w+(?!\\]))"; //$NON-NLS-1$

    private static final String PREFIX_TABLE_NAME_REGEXP = ""; //$NON-NLS-1$
    
    private static final String SUFFIX_TABLE_NAME = ""; //$NON-NLS-1$
    
    private static final String SUFFIX_TABLE_NAME_REGEXP = SUFFIX_TABLE_NAME;
    
    private static final String DOUBLE_ESCAPE = "\\"; //$NON-NLS-1$
    
    private static final String PREFIX_FIELD_NAME = "."; //$NON-NLS-1$
    
    private static final String PREFIX_FIELD_NAME_REGEXP = DOUBLE_ESCAPE + PREFIX_FIELD_NAME;
    
    private static final String SUFFIX_FIELD_NAME = ""; //$NON-NLS-1$
    
    private static final String SUFFIX_FIELD_NAME_REGEXP = SUFFIX_FIELD_NAME;
    
    private static final String SUBST_PATTERN_FOR_REPLACE_LOCATION = PREFIX_TABLE_NAME_REGEXP + "(\\s*){0}(\\s*)" //$NON-NLS-1$
    + SUFFIX_TABLE_NAME_REGEXP + "(\\s*)" + PREFIX_FIELD_NAME_REGEXP + "(\\s*){1}(\\s*)" + SUFFIX_FIELD_NAME_REGEXP; //$NON-NLS-1$ //$NON-NLS-2$
    
    private Perl5Matcher matcher = new Perl5Matcher();

    private Perl5Compiler compiler = new Perl5Compiler();

    private Pattern pattern;

    private PatternMatcherInput patternMatcherInput;

    public List<TableEntryLocation> parseTableEntryLocation(String expression) {
        List<TableEntryLocation> locations = new ArrayList<TableEntryLocation>();
        recompilePatternIfNecessary(EXPRESSION_PATTERN);
        patternMatcherInput = new PatternMatcherInput(expression);

        while (matcher.contains(patternMatcherInput, pattern)) {
            MatchResult matchResult = matcher.getMatch();
            if (matchResult.group(1) != null) {
                TableEntryLocation location = new TableEntryLocation(matchResult.group(1), matchResult.group(2),
                        matchResult.group(3), matchResult.group(6));
                locations.add(location);
            } else if (matchResult.group(matchResult.groups() - 1) != null) {
                TableEntryLocation location = new TableEntryLocation(matchResult.group(matchResult.groups() - 2),
                        matchResult.group(matchResult.groups() - 1));
                locations.add(location);
            }
        }
        return locations;

    }

    public List<String> getMatchedExpression(String expression) {
        List<String> matched = new ArrayList<String>();
        if (expression == null) {
            return matched;
        }
        recompilePatternIfNecessary(EXPRESSION_PATTERN);
        patternMatcherInput = new PatternMatcherInput(expression);
        while (matcher.contains(patternMatcherInput, pattern)) {
            MatchResult matchResult = matcher.getMatch();
            if (matchResult.group(0) != null) {
                matched.add(matchResult.group(0).trim());
            }
        }
        return matched;
    }

    private void recompilePatternIfNecessary(String regexpPattern) {
        if (pattern == null || !regexpPattern.equals(pattern.getPattern())) {
            try {
                pattern = compiler.compile(regexpPattern);
            } catch (MalformedPatternException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String replaceExpression(String expression, TableEntryLocation oldLocation, TableEntryLocation newLocation) {
        String returnedExpression = expression;
        String tempPattern = StringHelper.replacePrms(SUBST_PATTERN_FOR_REPLACE_LOCATION, new Object[] {
                oldLocation.getTableName(), oldLocation.getColumnValue() });
        recompilePatternIfNecessary(tempPattern);
        if (returnedExpression != null) {
            matcher.setMultiline(true);
            Perl5Substitution substitution = new Perl5Substitution(PREFIX_TABLE_NAME_REGEXP
                    + "$1" + newLocation.getTableName() + "$2" //$NON-NLS-1$ //$NON-NLS-2$
                    + SUFFIX_TABLE_NAME_REGEXP
                    + "$3" + PREFIX_FIELD_NAME_REGEXP + "$4" + newLocation.getColumnValue() + "$5" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    + SUFFIX_FIELD_NAME_REGEXP, Perl5Substitution.INTERPOLATE_ALL);
            returnedExpression = substitute(matcher, pattern, substitution, returnedExpression, Util.SUBSTITUTE_ALL);
        }
        return returnedExpression;
    }
    
    private String substitute(PatternMatcher matcher, Pattern pattern, Substitution sub, String input, int numSubs) {
        StringBuffer buffer = new StringBuffer(input.length());
        PatternMatcherInput pinput = new PatternMatcherInput(input);

        // Users have indicated that they expect the result to be the
        // original input string, rather than a copy, if no substitutions
        // are performed,
        if (substitute(buffer, matcher, pattern, sub, pinput, numSubs) != 0)
            return buffer.toString();
        return input;
    }
    
    private int substitute(StringBuffer result, PatternMatcher matcher, Pattern pattern, Substitution sub,
            PatternMatcherInput input, int numSubs) {
        int beginOffset, subCount;
        char[] inputBuffer;

        subCount = 0;
        beginOffset = input.getBeginOffset();
        inputBuffer = input.getBuffer();

        // Must be != 0 because SUBSTITUTE_ALL is represented by -1.
        // Do NOT change to numSubs > 0.
        while (numSubs != 0 && matcher.contains(input, pattern)) {
            --numSubs;
            ++subCount;
            if (input.getMatchBeginOffset() > 0) {
                if (input.getBuffer()[input.getMatchBeginOffset() - 1] == '_') {
                    continue;
                }
            }
            if (input.getMatchEndOffset() < input.length() - 1) {
                if (input.getBuffer()[input.getMatchEndOffset()] == '_') {
                    continue;
                }
            }
            result.append(inputBuffer, beginOffset, input.getMatchBeginOffset() - beginOffset);
            sub.appendSubstitution(result, matcher.getMatch(), subCount, input, matcher, pattern);
            beginOffset = input.getMatchEndOffset();
        }

        result.append(inputBuffer, beginOffset, input.length() - beginOffset);
        return subCount;
    }
}
