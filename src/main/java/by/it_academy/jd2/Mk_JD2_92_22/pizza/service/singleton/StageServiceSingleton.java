package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.StageDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.StageService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IStageService;

public class StageServiceSingleton {

    private IStageService service;
    private volatile static StageServiceSingleton instance;

    public StageServiceSingleton() {
        //try {
        this.service = new StageService(StageDaoSingleton.getInstance());
        // } catch (Exception e) {
        // throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        // }
    }

    public static IStageService getInstance() {
        if (instance == null) {
            synchronized (StageServiceSingleton.class) {
                if (instance == null) {
                    instance = new StageServiceSingleton();
                }
            }
        }
        return instance.service;
    }
}
