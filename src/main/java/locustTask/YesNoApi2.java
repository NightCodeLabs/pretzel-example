package locustTask;

public class YesNoApi2 extends AbstractYesNoApiTask {
	
    private static final String URLGETPARAM = "/?force=no";
    private static final String EXPECTED = "no";

	private int weight;

    public YesNoApi2(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "YesNo2 Api GET";
    }


    @Override
    public void execute() {
        super.commonTask(URLGETPARAM, EXPECTED);
    }

	
	
}
