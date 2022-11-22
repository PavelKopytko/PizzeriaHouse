package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.StageDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IStageDao;

public class StageDaoSingleton {

    private IStageDao storage;
    private volatile static StageDaoSingleton instance;

    public StageDaoSingleton() {
        try {
            this.storage = new StageDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IStageDao getInstance() {
        if (instance == null) {
            synchronized (StageDaoSingleton.class) {
                if (instance == null) {
                    instance = new StageDaoSingleton();
                }
            }
        }
        return instance.storage;
    }
}
