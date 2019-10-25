import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class Detect {

    public static void main(String[] args) throws Exception {
        ArrayList<String> Dict = new ArrayList<>();
        ArrayList<String> Candidate = new ArrayList<>();
        ArrayList<String> Blend = new ArrayList<>();

        BufferedReader bfCandidate = new BufferedReader(new FileReader("src/candidates.txt"));
        BufferedReader bfDict = new BufferedReader(new FileReader("src/dict.txt"));
        BufferedReader bfBlend = new BufferedReader(new FileReader("src/blends.txt"));


        String StrCandiate;
        StrCandiate = bfCandidate.readLine();
        int CountCandidate = 0;

        while (StrCandiate!=null){
            Candidate.add(StrCandiate);
            CountCandidate++;
            StrCandiate = bfCandidate.readLine();
        }
        System.out.println(CountCandidate);

        String StrDict = bfDict.readLine();

        while (StrDict!=null){
            Dict.add(StrDict);
            StrDict = bfDict.readLine();
        }

        int CountBlend=0;

        String StrBlend = bfBlend.readLine();

        while (StrBlend!=null){
            String StrBlendSP[] = StrBlend.split("\t");
            Blend.add(StrBlendSP[0]);
            CountBlend++;
            StrBlend = bfBlend.readLine();
        }
        System.out.println(CountBlend);


        String pattern = ".*(\\w)\\1{2,}.*";
        String pattern2 = ".*(\\w\\w)\\1{1,}.*";
        String pattern3 = "(a|k|o|q|r|u|x|z).*";

        EditDistance ed = new EditDistance();
        int Pred = 0;
        int CorrectPred = 0;

        for (int i=0;i<Candidate.size();i++){
            int count1 = 0;
            int count2 = 0;

            if(!Pattern.matches(pattern, Candidate.get(i)) && !Pattern.matches(pattern3,Candidate.get(i))
                    && !Pattern.matches(pattern2, Candidate.get(i)) && Candidate.get(i).length()>3) {

                for (int j = 0; j < Dict.size(); j++) {

                    if (ed.ld(Candidate.get(i), Dict.get(j)) == 1 ) {

                        if((Candidate.get(i).charAt(0)!=Dict.get(j).charAt(0))||
                                (Candidate.get(i).charAt(1)!=Dict.get(j).charAt(1))){
                            count1++;
                        }

                        else if((Candidate.get(i).charAt(Candidate.get(i).length()-1)!=Dict.get(j).charAt(Dict.get(j).length()-1))||
                                (Candidate.get(i).charAt(Candidate.get(i).length()-2)!=Dict.get(j).charAt(Dict.get(j).length()-2))){
                            count2++;
                        }
                    }

                    else if (ed.ld(Candidate.get(i), Dict.get(j)) == 2 ) {

                        if((Candidate.get(i).charAt(0)!=Dict.get(j).charAt(0))&&
                                (Candidate.get(i).charAt(1)!=Dict.get(j).charAt(1))){
                            count1++;}

                        else if((Candidate.get(i).charAt(Candidate.get(i).length()-1)!=Dict.get(j).charAt(Dict.get(j).length()-1))&&
                                (Candidate.get(i).charAt(Candidate.get(i).length()-2)!=Dict.get(j).charAt(Dict.get(j).length()-2))){
                            count2++;
                        }
                    }




                }
            }

            if(count1>0 || count2>0) {
                Pred++;
                System.out.println(Pred+":"+Candidate.get(i));
                for (int k=0;k<Blend.size();k++){
                    if (Candidate.get(i).equals(Blend.get(k))){
                        CorrectPred++;
                        System.out.println(CorrectPred+" Correct!!!!!!!!!!!!!!!!!!!!!");
                    }

                }
            }

        }

        JaroWinklerDistance jw = new JaroWinklerDistance();
        double jwpred=0;
        double jwcorr=0;

        for (int i=0;i<Candidate.size();i++){
            int count = 0;

            if(!Pattern.matches(pattern, Candidate.get(i)) && !Pattern.matches(pattern3,Candidate.get(i))
                    && !Pattern.matches(pattern2, Candidate.get(i)) && Candidate.get(i).length()>3) {

                for (int j = 0; j < Dict.size(); j++) {

                    if (jw.getJaroWinklerDistance(Candidate.get(i), Dict.get(j)) > 0.9 )
                        count++;

                }
            }

            if(count>1) {
                jwpred++;
                System.out.println(jwpred+":"+Candidate.get(i));
                for (int k=0;k<Blend.size();k++){
                    if (Candidate.get(i).equals(Blend.get(k))){
                        jwcorr++;
                        System.out.println(jwcorr+" Correct!!!!!!!!!!!!!!!!!!!!!");
                    }

                }
            }

        }


        System.out.println("edPred: "+Pred);
        System.out.println("edCorrectPred: "+CorrectPred);
        System.out.println("jwPred: "+jwpred);
        System.out.println("jwCorrectPred: "+jwcorr);
        System.out.println("CountBlend: "+CountBlend);
        System.out.println("CountCandidate: "+CountCandidate);

        double Prec = CorrectPred*1.0/Pred*1.0;
        double Recall = CorrectPred*1.0/CountBlend*1.0;

        System.out.println("edPrecision: "+Prec*100+"%");
        System.out.println("edRecall: "+Recall*100+"%");
        System.out.println("jwPrecision: "+jwcorr/jwpred*100+"%");
        System.out.println("jwRecall: "+jwcorr/(CountBlend*1.0)*100+"%");
    }



}
