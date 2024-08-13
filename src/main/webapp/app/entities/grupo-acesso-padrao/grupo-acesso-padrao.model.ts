export interface IGrupoAcessoPadrao {
  id: number;
  nome?: string | null;
}

export type NewGrupoAcessoPadrao = Omit<IGrupoAcessoPadrao, 'id'> & { id: null };
