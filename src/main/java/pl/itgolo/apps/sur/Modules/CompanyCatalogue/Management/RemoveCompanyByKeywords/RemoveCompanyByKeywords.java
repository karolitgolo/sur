package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Management.RemoveCompanyByKeywords;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.itgolo.apps.sur.Modules.Commons.Abstracts.ActionSites;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;
import pl.itgolo.plugin.helper.Sound.BeepHelper;
import pl.itgolo.plugin.helper.Sound.Exceptions.BeepHelperException;

import javax.swing.*;

/**
 * The type Remove company by data.
 */
public class RemoveCompanyByKeywords extends ActionSites {

    /**
     * The constant onBeep.
     */
    public static Boolean turnOnBeep = true;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        RemoveCompanyByKeywords removeCompanyByData = new RemoveCompanyByKeywords();
        removeCompanyByData.launch();
    }

    @Override
    protected void action() throws Exception {
        if (turnOnBeep){
            BeepHelper.getSoundBeep06().play();
        }
        for (String site : this.sites) {
            site = "http://" + site;
            LogService.debug("Action remove company by data on site: " + site);
            CompanyCatalogueUtils.authorization(this.env.getProperty("NEW_APP_APP_EMAIL_ADMINISTRATOR"), this.env.getProperty("NEW_APP_APP_PASSWORD_ADMINISTRATOR"), site, this.browser);
            this.browser.actions.navigateTo(site + "/wp-admin/edit.php?s=" + env.getProperty("REMOVE_DATA_COMPANY_KEYWORDS"));
            Elements posts = this.browser.actions.getElements("#the-list tr.iedit");
            LogService.test("Found quantity posts: " + posts.size());
            Integer counterRemove = 0;
            for (Element post : posts) {
                counterRemove = removePostWithQuestion(post);
            }
            if (!counterRemove.equals(0)){
                emptyTrash(site);
            }
        }
    }

    private void emptyTrash(String site) throws ChromiumException {
        this.browser.actions.navigateTo(site + "/wp-admin/edit.php?post_status=trash&post_type=post");
        Elements postsTrash = this.browser.actions.getElements("#the-list tr.iedit");
        LogService.test("Found quantity posts in trash: " + postsTrash.size());
        if (postsTrash.size()> 0){
            this.browser.actions.clickReload("#delete_all", 60);
            this.browser.actions.waitElement("#message", 15);
        }
    }

    private Integer removePostWithQuestion(Element post) throws ChromiumException, BeepHelperException {
        Integer counterRemove = 0;
        String title =  post.selectFirst(".row-title").html();
        JDialog.setDefaultLookAndFeelDecorated(true);
        if (turnOnBeep){
            BeepHelper.getSoundBeep06().play();
        }
        Integer response = JOptionPane.showConfirmDialog(null, String.format("Czy usunąć firmę '%1$s'?", title), "Potwierdzenie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            String trashLink = post.selectFirst(".trash a").attr("href");
            LogService.test("Trash link: " + trashLink);
            this.browser.actions.navigateTo(trashLink);
            counterRemove++;
        }
        return counterRemove;
    }
}
