package no.java.swing;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:yngvars@gmail.no">Yngvar S&oslash;rensen</a>
 */
public final class TextWithMnemonic {

    private static final String MNEMONIC_REG_EXP = "\\&(\\w|\\d)";
    private static final Pattern MNEMONIC_PATTERN = Pattern.compile(MNEMONIC_REG_EXP);

    private String textWithMnemonic;
    private String textWithoutMnemonic;
    private Integer mnemonic;
    private Integer mnemonicIndex;

    public TextWithMnemonic(final String textWithMnemonic) {
        Validate.notNull(textWithMnemonic, "Text can not be null");
        this.textWithMnemonic = textWithMnemonic;
        Matcher matcher = MNEMONIC_PATTERN.matcher(textWithMnemonic);
        if (matcher.find()) {
            String mnemonicCharacter = matcher.group(1);
            mnemonicIndex = matcher.start(1) - 1;
            mnemonic = (int)Character.toUpperCase(mnemonicCharacter.charAt(0));
            if (matcher.find()) {
                throw new IllegalArgumentException("Only one mnemonic per string allowed: " + textWithMnemonic);
            }
        }
        textWithoutMnemonic = matcher.replaceAll("$1");
    }

    public String getTextWithMnemonic() {
        return textWithMnemonic;
    }

    public String getTextWithoutMnemonic() {
        return textWithoutMnemonic;
    }

    public Integer getMnemonic() {
        return mnemonic;
    }

    public Integer getMnemonicIndex() {
        return mnemonicIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other.getClass().equals(getClass()) && EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}