package blackmafia.gogoanime;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constants {
    public static String GlobalUrl = "https://www1.gogoanimes.ai";
    public static ArrayList<model_categories_list> CategoryList = new ArrayList<>();

    public ArrayList<model_categories_list> FillArrayList(){
        AddModel("Action","https://gogocdn.net/cover/shironeko-project-zero-chronicle.png");
        AddModel("Cars","https://gogocdn.net/images/upload/72246.jpg");
        AddModel("Dementia","https://gogocdn.net/cover/yami-shibai-4.png");
        AddModel("Drama","https://gogocdn.net/cover/magia-record-mahou-shoujo-madokamagica-gaiden-tv.png");
        AddModel("Ecchi","https://gogocdn.net/cover/miru-tights.png");
        AddModel("Game","https://gogocdn.net/cover/sword-art-online-alicization-war-of-underworld-dub.png");
        AddModel("Historical","https://gogocdn.net/cover/kingdom-3rd-season.png");
        AddModel("Josei","https://gogocdn.net/cover/try-knights.png");
        AddModel("Magic","https://gogocdn.net/cover/kiratto-prichan-season-3.png");
        AddModel("Mecha","https://gogocdn.net/cover/code-geass-fukkatsu-no-lelouch.png");
        AddModel("Music","https://gogocdn.net/cover/listeners.png");
        AddModel("Parody","https://gogocdn.net/cover/isekai-quartet.png");
        AddModel("Psychological","https://gogocdn.net/cover/psycho-pass-3-first-inspector.png");
        AddModel("Samurai","https://gogocdn.net/cover/gintama-2017.png");
        AddModel("Sci-fi","https://gogocdn.net/cover/altered-carbon-resleeved-dub.png");
        AddModel("Shoujo","https://gogocdn.net/cover/7-seeds.png");
        AddModel("Shounen","https://gogocdn.net/cover/kyokou-suiri-dub.png");
        AddModel("Slice of life","https://gogocdn.net/cover/koisuru-asteroid-dub.png");
        AddModel("Sports","https://gogocdn.net/cover/major-2nd-tv-2nd-season.png");
        AddModel("Supernatural","https://gogocdn.net/cover/zashiki-warashi-no-tatami-chan.png");
        AddModel("Vampire","https://gogocdn.net/cover/hellsing-ultimate-dub.png");
        AddModel("Yuri","https://gogocdn.net/images/b.jpg");
        AddModel("Adventure","https://gogocdn.net/cover/shachou-battle-no-jikan-desu.png");
        AddModel("Comedy","https://gogocdn.net/cover/kaguya-sama-wa-kokurasetai-tensai-tachi-no-renai-zunousen-2.png");
        AddModel("Demons","https://gogocdn.net/cover/kimetsu-no-yaiba.png");
        AddModel("Dub","https://gogocdn.net/cover/hunter-x-hunter-2011-dub.png");
        AddModel("Fantasy","https://gogocdn.net/cover/shironeko-project-zero-chronicle.png");
        AddModel("Harem","https://gogocdn.net/cover/hyakuren-no-haou-to-seiyaku-no-valkyria-dub.png");
        AddModel("Horror","https://gogocdn.net/cover/kyochuu-rettou-movie.png");
        AddModel("Kids","https://gogocdn.net/cover/pokemon-2019.png");
        AddModel("Martial Arts","https://gogocdn.net/cover/kengan-ashura.png");
        AddModel("Military","https://gogocdn.net/cover/girls-panzer-saishuushou-part-2.png");
        AddModel("Mystery","https://gogocdn.net/cover/kakegurui-xx.png");
        AddModel("Police","https://gogocdn.net/cover/keishichou-tokumubu-tokushu-kyouakuhan-taisakushitsu-dainanaka-tokunana.png");
        AddModel("Romance","https://gogocdn.net/cover/kaguya-sama-wa-kokurasetai-tensai-tachi-no-renai-zunousen.png");
        AddModel("School","https://gogocdn.net/cover/yahari-ore-no-seishun-love-comedy-wa-machigatteiru-kan.png");
        AddModel("Seinen","https://gogocdn.net/cover/konohana-kitan-dub.png");
        AddModel("Shoujo Ai","https://gogocdn.net/cover/mini-yuri.png");
        AddModel("Shounen Ai","https://gogocdn.net/cover/jie-yao.png");
        AddModel("Space","https://gogocdn.net/cover/kanata-no-astra.png");
        AddModel("Super Power","https://gogocdn.net/cover/super-dragon-ball-heroes-big-bang-mission.png");
        AddModel("Thriller","https://gogocdn.net/cover/ling-long-incarnation.png");
        AddModel("Yaoi","https://gogocdn.net/cover/papa-datte-shitai.png");

        return CategoryList;

    }
    public void AddModel(String name, String Link){
        model_categories_list model = null;
        model = new model_categories_list(name,Link);
        CategoryList.add(model);
    }
}
