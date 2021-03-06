package com.turlir.abakgists.model;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.turlir.abakgists.R;

public class GistModel implements InterfaceModel, Identifiable<GistModel> {

    public final String id;

    public final String url;

    public final String created;

    @NonNull
    public final String description;

    @Nullable
    public final String ownerLogin;

    @Nullable
    public final String ownerAvatarUrl;

    @NonNull
    public final String note;

    private final boolean isLocal;

    public GistModel(String id, String url, String created, @NonNull String description,
                     @Nullable String ownerLogin, @Nullable String ownerAvatarUrl, @NonNull String note, boolean isLocal) {
        this.id = id;
        this.url = url;
        this.created = created;
        this.description = description;
        this.ownerLogin = ownerLogin;
        this.ownerAvatarUrl = ownerAvatarUrl;
        this.note = note;
        this.isLocal = isLocal;
    }

    public GistModel(GistModel other, @NonNull String desc, @NonNull String note) {
        id = other.id;
        url = other.url;
        created = other.created;
        ownerLogin = other.ownerLogin;
        ownerAvatarUrl = other.ownerAvatarUrl;
        isLocal = other.isLocal;

        this.description = desc;
        this.note = note;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * различны ли элементы с точки зрения базы данных
     * @param other сравниваемый элемент
     * @return {@code true} - различны, иначе {@code false}
     */
    @Override
    public boolean isDifferent(@NonNull GistModel other) {
        return !other.id.equals(this.id);
    }

    /**
     * формирует ссылку на github.com
     * @return ссылка на этот гист
     */
    public String insteadWebLink() {
        if (ownerLogin != null) {
            return (String.format("http://gist.github.com/%s/%s", ownerLogin, id));
        } else {
            return (String.format("http://gist.github.com/%s", id));
        }
    }

    /**
     * Формирует строковое представления автора гиста
     * @param cnt ресурсы
     * @return логин автора или строка-заглушка
     */
    public String login(Resources cnt) {
        if (ownerLogin == null) {
            return cnt.getString(R.string.anonymous);
        } else {
            return ownerLogin;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("GistModel {")
                .append(" id=").append(id).append(",\n")
                .append(" url=").append(url).append(",\n")
                .append(" created=").append(created).append(",\n")
                .append(" description=").append(description).append(",\n")
                .append(" ownerLogin=").append(ownerLogin).append(",\n")
                .append(" ownerAvatarUrl=").append(ownerAvatarUrl).append(",\n")
                .append(" note=").append(note).append(",\n")
                .append(" isLocal=").append(isLocal).append(",\n")
                .append('}').toString();
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 167 * result + url.hashCode();
        result = 167 * result + created.hashCode();
        result = 167 * result + description.hashCode();
        result = 167 * result + (ownerLogin != null ? ownerLogin.hashCode() : 0);
        result = 167 * result + (ownerAvatarUrl != null ? ownerAvatarUrl.hashCode() : 0);
        result = 167 * result + note.hashCode();
        result = 167 * result + (isLocal ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GistModel gistModel = (GistModel) o;

        if (!id.equals(gistModel.id)) return false;
        if (!url.equals(gistModel.url)) return false;
        if (!created.equals(gistModel.created)) return false;

        if (ownerLogin != null) {
            if (!ownerLogin.equals(gistModel.ownerLogin)) return false;
        } else {
            if (gistModel.ownerLogin != null) return false;
        }

        if (ownerAvatarUrl != null) {
            if (!ownerAvatarUrl.equals(gistModel.ownerAvatarUrl)) return false;
        } else {
            if (gistModel.ownerAvatarUrl != null) return false;
        }

        if (isLocal != gistModel.isLocal) return false;

        return gistModel.description.equals(description) && gistModel.note.equals(note);
    }
}
