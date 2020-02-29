package locustTask;

public class YesNoApi extends AbstractYesNoApiTask {
	
	private static final String URLGETPARAM = "/?force=yes";
    private static final String EXPECTED = "yes";

	private int weight;

    public YesNoApi(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "YesNo Api GET";
    }


    @Override
    public void execute() {
        super.commonTask(URLGETPARAM, EXPECTED);
    }

	
	
}
