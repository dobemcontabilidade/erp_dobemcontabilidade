import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../grupo-acesso-empresa-usuario-contador.test-samples';

import {
  GrupoAcessoEmpresaUsuarioContadorService,
  RestGrupoAcessoEmpresaUsuarioContador,
} from './grupo-acesso-empresa-usuario-contador.service';

const requireRestSample: RestGrupoAcessoEmpresaUsuarioContador = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
};

describe('GrupoAcessoEmpresaUsuarioContador Service', () => {
  let service: GrupoAcessoEmpresaUsuarioContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoAcessoEmpresaUsuarioContador | IGrupoAcessoEmpresaUsuarioContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoAcessoEmpresaUsuarioContadorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a GrupoAcessoEmpresaUsuarioContador', () => {
      const grupoAcessoEmpresaUsuarioContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoAcessoEmpresaUsuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoAcessoEmpresaUsuarioContador', () => {
      const grupoAcessoEmpresaUsuarioContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoAcessoEmpresaUsuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoAcessoEmpresaUsuarioContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoAcessoEmpresaUsuarioContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoAcessoEmpresaUsuarioContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing', () => {
      it('should add a GrupoAcessoEmpresaUsuarioContador to an empty array', () => {
        const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing([], grupoAcessoEmpresaUsuarioContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoEmpresaUsuarioContador);
      });

      it('should not add a GrupoAcessoEmpresaUsuarioContador to an array that contains it', () => {
        const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = sampleWithRequiredData;
        const grupoAcessoEmpresaUsuarioContadorCollection: IGrupoAcessoEmpresaUsuarioContador[] = [
          {
            ...grupoAcessoEmpresaUsuarioContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          grupoAcessoEmpresaUsuarioContadorCollection,
          grupoAcessoEmpresaUsuarioContador,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoAcessoEmpresaUsuarioContador to an array that doesn't contain it", () => {
        const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = sampleWithRequiredData;
        const grupoAcessoEmpresaUsuarioContadorCollection: IGrupoAcessoEmpresaUsuarioContador[] = [sampleWithPartialData];
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          grupoAcessoEmpresaUsuarioContadorCollection,
          grupoAcessoEmpresaUsuarioContador,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoEmpresaUsuarioContador);
      });

      it('should add only unique GrupoAcessoEmpresaUsuarioContador to an array', () => {
        const grupoAcessoEmpresaUsuarioContadorArray: IGrupoAcessoEmpresaUsuarioContador[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const grupoAcessoEmpresaUsuarioContadorCollection: IGrupoAcessoEmpresaUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          grupoAcessoEmpresaUsuarioContadorCollection,
          ...grupoAcessoEmpresaUsuarioContadorArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = sampleWithRequiredData;
        const grupoAcessoEmpresaUsuarioContador2: IGrupoAcessoEmpresaUsuarioContador = sampleWithPartialData;
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          [],
          grupoAcessoEmpresaUsuarioContador,
          grupoAcessoEmpresaUsuarioContador2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoEmpresaUsuarioContador);
        expect(expectedResult).toContain(grupoAcessoEmpresaUsuarioContador2);
      });

      it('should accept null and undefined values', () => {
        const grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          [],
          null,
          grupoAcessoEmpresaUsuarioContador,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoEmpresaUsuarioContador);
      });

      it('should return initial array if no GrupoAcessoEmpresaUsuarioContador is added', () => {
        const grupoAcessoEmpresaUsuarioContadorCollection: IGrupoAcessoEmpresaUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing(
          grupoAcessoEmpresaUsuarioContadorCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(grupoAcessoEmpresaUsuarioContadorCollection);
      });
    });

    describe('compareGrupoAcessoEmpresaUsuarioContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoAcessoEmpresaUsuarioContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoAcessoEmpresaUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresaUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoAcessoEmpresaUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresaUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoAcessoEmpresaUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresaUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
