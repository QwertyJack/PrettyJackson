public class Term implements Comparable<Term> {
    public String _term;
    public String _byte;
    public int _freq;

    public Term(String _term, String _byte, int _freq) {
        this._term = _term;
        this._byte = _byte;
        this._freq = _freq;
    }

    @Override
    public int compareTo(Term term) {
        if (this._freq < term._freq) {
            return -1;
        } else if (this._freq > term._freq) {
            return 1;
        } else {
            return this._term.compareTo(term._term);
        }
    }
}
