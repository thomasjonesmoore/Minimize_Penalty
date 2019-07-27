public class Penalty{
    int penaltyNumber;
    int index;

    Penalty(int p, int i){
	penaltyNumber = p;
	index = i;
    }    

    int getPenalty(){
	return penaltyNumber;
    }

    void setIndex(int i){
	index = i;
    }

    void setPenalty(int p){
	penaltyNumber = p;
    }

    int getIndex(){
	return index;
    }
}
