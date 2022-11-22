package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api;

import java.util.List;

public interface ISelectedItemService<DtoType>{ //extends IService<SelectedItemDto> {

    DtoType create(DtoType item);// throws ServiceException, ValidateException, NotUniqServiceException;

    DtoType read(long id);// throws IDServiceException, ServiceException;

    List<DtoType> get();// throws ServiceException;

    DtoType update(long id, long dtUpdate, DtoType item);// throws ValidateException, ServiceException;

    void delete(long id, long dtUpdate);// throws ServiceException;
}
