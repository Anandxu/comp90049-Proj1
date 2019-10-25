public class JaroWinklerDistance {

    private float p = 0.1f;
    private final float MAX_P = 0.25f;
    private final int MAX_L = 4;


    private void setP(float p){
        this.p = p;
    }

    public float getJaroDistance(CharSequence s1,CharSequence s2){
        if (s1 == null || s2 == null) return 0f;
        int result[] = matches(s1,s2);
        float m = result[0];
        if (m == 0f)
            return 0f;

        float j = ((m / s1.length() + m / s2.length() + (m - result[1]) / m)) / 3;
        return j;
    }

    public float getJaroWinklerDistance(CharSequence s1,CharSequence s2){
        if (s1 == null || s2 == null) return 0f;
        int result[] = matches(s1,s2);

        float m = result[0];
        if (m == 0f)
            return 0f;

        float j = ((m / s1.length() + m / s2.length() + (m - result[1]) / m)) / 3;
        float jw = j + Math.min(p,MAX_P) * result[2] * (1 - j);
        return jw;


    }

    private int[] matches(CharSequence s1,CharSequence s2){

        CharSequence max,min;
        if (s1.length() > s2.length()){
            max = s1;
            min = s2;
        }else{
            max = s2;
            min = s1;
        }


        int matchedWindow = Math.max(max.length() / 2 - 1,0);

        boolean[] minMatchFlag = new boolean[min.length()];
        boolean[] maxMatchFlag = new boolean[max.length()];
        int matches = 0;

        for (int i = 0;i < min.length();i++){
            char minChar = min.charAt(i);

            for (int j = Math.max(i - matchedWindow,0);
                 j < Math.min(i + matchedWindow + 1,max.length());j++){
                if (!maxMatchFlag[j] && minChar == max.charAt(j)){
                    maxMatchFlag[j] = true;
                    minMatchFlag[i] = true;
                    matches++;
                    break;
                }
            }
        }

        int transpositions = 0;
        int prefix = 0;

        int j = 0;
        for (int i = 0;i < min.length();i++){
            if (minMatchFlag[i]){
                while (!maxMatchFlag[j]) j++;

                if (min.charAt(i) != max.charAt(j)){
                    transpositions++;
                }
                j++;
            }
        }

        for(int i = 0;i < min.length();i++){
            if (s1.charAt(i) == s2.charAt(i)){
                prefix++;
            }else {
                break;
            }
        }

        return new int[]{matches,transpositions / 2,prefix > MAX_L ? MAX_L : prefix};
    }

}
