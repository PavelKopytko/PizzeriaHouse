package by.it_academy.jd2.Mk_JD2_92_22.main.entity;

public class Employee {
    private long id;
    private String name;
    private Long job;
    private Long dep;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getJob() {
        return job;
    }

    public void setJob(Long job) {
        this.job = job;
    }

    public Long getDep() {
        return dep;
    }

    public void setDep(Long dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job=" + job +
                ", dep=" + dep +
                '}';
    }
}
