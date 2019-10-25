public class EditDistance {

    public int ld(String word1, String word2)
    {
        int d[][];
        int n = word1.length();
        int m = word2.length();
        int temp;
        int i;
        int j;
        int k;
        char ch1;
        char ch2;

        if(n == 0) {
            return m*3;
        }

        if(m == 0) {
            return n;
        }

        d = new int[n+1][m+1];

        for(i = 0; i <=n ; i++) {
            d[i][0] = i*3;
        }

        for(k = 0; k <= m; k++) {
            d[0][k] = k;
        }

        for(i = 1; i <= n; i++) {
            ch1 = word1.charAt(i - 1);
            for(j = 1; j <= m; j++) {
                ch2 = word2.charAt(j - 1);
                if(ch1 == ch2) {
                    temp = 0;
                }else {
                    temp = 1;
                }
                d[i][j] = min(d[i - 1][j] + 3, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

}
