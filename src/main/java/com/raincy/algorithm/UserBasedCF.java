package com.raincy.algorithm;

import com.raincy.entity.Article;
import com.raincy.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class UserBasedCF {
    /**
     * 查找最近邻
     */
    private Map<Double, String> computeNearestUser(String username, List<User> users) {
        Map<Double, String> dist = new TreeMap<>();
        User user1 = new User(username);
        for (User user : users) {
            if (username.equals(user.username)) {
                user1 = user;
            }
        }
        for (User user2 : users) {
            if (!user2.username.equals(username)) {
                double dist_scr = pearsonDis(user2.articleList, user1.articleList);
                dist.put(dist_scr, user2.username);
            }
        }
        return dist;
    }

    /**
     * 计算皮尔森相关系数
     */
    private double pearsonDis(List<Article> rating1, List<Article> rating2) {
        int n = rating1.size();
        List<Integer> rating1LikesCollect = rating1
                .stream()
                .map(A -> A.likes)
                .toList();
        List<Integer> rating2LikesCollect = rating2
                .stream()
                .map(A -> A.likes)
                .toList();
        double Ex = rating1LikesCollect
                .stream()
                .mapToDouble(x -> x)
                .sum();
        double Ey = rating2LikesCollect
                .stream()
                .mapToDouble(y -> y)
                .sum();
        double _Ex = rating1LikesCollect
                .stream()
                .mapToDouble(x -> Math.pow(x, 2))
                .sum();
        double _Ey = rating2LikesCollect
                .stream()
                .mapToDouble(y -> Math.pow(y, 2))
                .sum();
        double Exy = IntStream
                .range(0, n)
                .mapToDouble(i -> rating1LikesCollect.get(i) * rating2LikesCollect.get(i))
                .sum();
        double numerator = Exy - Ex * Ey / n;
        double denominator = Math.sqrt((_Ex - Math.pow(Ex, 2) / n) * (_Ey - Math.pow(Ey, 2) / n));
        if (denominator == 0) {
            return 0.0;
        }
        return numerator / denominator;
    }

    /**
     * 推荐函数
     */
    public List<Article> recommend(String username, List<User> users) {
        Map<Double, String> dist = computeNearestUser(username, users);
        String nearest = dist
                .values()
                .iterator()
                .next();
        User neighborRatings = new User();
        for (User user : users) {
            if (nearest.equals(user.username)) {
                neighborRatings = user;
            }
        }
        User userRatings = new User();
        for (User user : users) {
            if (username.equals(user.username)) {
                userRatings = user;
            }
        }
        List<Article> recommendationArticles = new ArrayList<>();
        for (Article article : neighborRatings.articleList) {
            if (userRatings.findArticle(article.articleTitle) == null) {
                recommendationArticles.add(article);
            }
        }
        recommendationArticles.sort(Article::compareTo);
        return recommendationArticles;
    }
}
