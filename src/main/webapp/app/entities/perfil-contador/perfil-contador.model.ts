export interface IPerfilContador {
  id: number;
  perfil?: string | null;
  descricao?: string | null;
  limiteEmpresas?: number | null;
  limiteDepartamentos?: number | null;
  limiteFaturamento?: number | null;
}

export type NewPerfilContador = Omit<IPerfilContador, 'id'> & { id: null };
