import com.fasterxml.jackson.core.util.DefaultIndenter;

public class InlineConfigurableIndenter extends DefaultIndenter {

    public static final InlineConfigurableIndenter SYSTEM_LINEFEED_INSTANCE = new InlineConfigurableIndenter("  ", SYS_LF);
    protected boolean inline = false;

    public InlineConfigurableIndenter() {
        this("  ", SYS_LF);
    }

    public InlineConfigurableIndenter(String indent, String eol) {
        super(indent, eol);
    }

    @Override
    public boolean isInline() {
        return this.inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
    }
}
