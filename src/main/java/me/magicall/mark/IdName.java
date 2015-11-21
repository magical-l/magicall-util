/**
 * 
 */
package me.magicall.mark;

import me.magicall.mark.HasIdGetter.HasIntIdGetter;

public interface IdName extends HasIntIdGetter, Named {

	class SimpleIdNameImpl implements IdName {

		public final int id;
		public final String name;

		public SimpleIdNameImpl(final int id, final String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public Integer getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final SimpleIdNameImpl other = (SimpleIdNameImpl) obj;
			return id == other.getId();
		}

		@Override
		public String toString() {
			return "IdName [id=" + id + ", name=" + name + ']';
		}
	}
}
