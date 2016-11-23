package oose.dea.services.local;

import oose.dea.dao.FakeItemDAO;
import oose.dea.dao.ItemDAO;
import oose.dea.domain.Item;
import oose.dea.services.ItemService;

import javax.inject.Inject;
import java.util.List;

public class LocalItemService implements ItemService {
    @com.google.inject.Inject
    private ItemDAO itemDAO;

    @Override
    public List<Item> findAll() {
        return itemDAO.list();
    }
}