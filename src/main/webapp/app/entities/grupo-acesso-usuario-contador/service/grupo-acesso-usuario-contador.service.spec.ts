import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../grupo-acesso-usuario-contador.test-samples';

import { GrupoAcessoUsuarioContadorService, RestGrupoAcessoUsuarioContador } from './grupo-acesso-usuario-contador.service';

const requireRestSample: RestGrupoAcessoUsuarioContador = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
};

describe('GrupoAcessoUsuarioContador Service', () => {
  let service: GrupoAcessoUsuarioContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoAcessoUsuarioContador | IGrupoAcessoUsuarioContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoAcessoUsuarioContadorService);
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

    it('should create a GrupoAcessoUsuarioContador', () => {
      const grupoAcessoUsuarioContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoAcessoUsuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoAcessoUsuarioContador', () => {
      const grupoAcessoUsuarioContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoAcessoUsuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoAcessoUsuarioContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoAcessoUsuarioContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoAcessoUsuarioContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoAcessoUsuarioContadorToCollectionIfMissing', () => {
      it('should add a GrupoAcessoUsuarioContador to an empty array', () => {
        const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing([], grupoAcessoUsuarioContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoUsuarioContador);
      });

      it('should not add a GrupoAcessoUsuarioContador to an array that contains it', () => {
        const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = sampleWithRequiredData;
        const grupoAcessoUsuarioContadorCollection: IGrupoAcessoUsuarioContador[] = [
          {
            ...grupoAcessoUsuarioContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing(
          grupoAcessoUsuarioContadorCollection,
          grupoAcessoUsuarioContador,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoAcessoUsuarioContador to an array that doesn't contain it", () => {
        const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = sampleWithRequiredData;
        const grupoAcessoUsuarioContadorCollection: IGrupoAcessoUsuarioContador[] = [sampleWithPartialData];
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing(
          grupoAcessoUsuarioContadorCollection,
          grupoAcessoUsuarioContador,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoUsuarioContador);
      });

      it('should add only unique GrupoAcessoUsuarioContador to an array', () => {
        const grupoAcessoUsuarioContadorArray: IGrupoAcessoUsuarioContador[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const grupoAcessoUsuarioContadorCollection: IGrupoAcessoUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing(
          grupoAcessoUsuarioContadorCollection,
          ...grupoAcessoUsuarioContadorArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = sampleWithRequiredData;
        const grupoAcessoUsuarioContador2: IGrupoAcessoUsuarioContador = sampleWithPartialData;
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing(
          [],
          grupoAcessoUsuarioContador,
          grupoAcessoUsuarioContador2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoUsuarioContador);
        expect(expectedResult).toContain(grupoAcessoUsuarioContador2);
      });

      it('should accept null and undefined values', () => {
        const grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing([], null, grupoAcessoUsuarioContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoUsuarioContador);
      });

      it('should return initial array if no GrupoAcessoUsuarioContador is added', () => {
        const grupoAcessoUsuarioContadorCollection: IGrupoAcessoUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoUsuarioContadorToCollectionIfMissing(grupoAcessoUsuarioContadorCollection, undefined, null);
        expect(expectedResult).toEqual(grupoAcessoUsuarioContadorCollection);
      });
    });

    describe('compareGrupoAcessoUsuarioContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoAcessoUsuarioContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoAcessoUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoAcessoUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoAcessoUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
