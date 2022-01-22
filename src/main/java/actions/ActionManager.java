package actions;

public class ActionManager {

    private static ActionManager instance = null;

    public static ActionManager getInstance() {
        if (instance == null)
            instance = new ActionManager();
        return instance;
    }

    private AddAction addAction;
    private DeleteAction deleteAction;
    private UpdateAction updateAction;
    private FilterSortAction filterSortAction;
    private RelationsAction relationsAction;
    private ReportsAction reportsAction;
    private SearchAction searchAction;

    public ActionManager() {
        addAction = new AddAction();
        deleteAction = new DeleteAction();
        updateAction = new UpdateAction();
        filterSortAction = new FilterSortAction();
        relationsAction = new RelationsAction();
        reportsAction=new ReportsAction();
        searchAction=new SearchAction();
    }

    public AddAction getAddAction() {
        return addAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public UpdateAction getUpdateAction() {
        return updateAction;
    }

    public FilterSortAction getFilterSortAction() {
        return filterSortAction;
    }

    public RelationsAction getRelationsAction() {
        return relationsAction;
    }

    public void setAddAction(AddAction addAction) {
        this.addAction = addAction;
    }

    public void setDeleteAction(DeleteAction deleteAction) {
        this.deleteAction = deleteAction;
    }

    public void setUpdateAction(UpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    public ReportsAction getReportsAction() {
        return reportsAction;
    }

    public SearchAction getSearchAction() {
        return searchAction;
    }
}

