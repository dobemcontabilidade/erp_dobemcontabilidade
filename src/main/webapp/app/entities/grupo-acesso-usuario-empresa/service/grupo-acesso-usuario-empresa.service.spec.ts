import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../grupo-acesso-usuario-empresa.test-samples';

import { GrupoAcessoUsuarioEmpresaService, RestGrupoAcessoUsuarioEmpresa } from './grupo-acesso-usuario-empresa.service';

const requireRestSample: RestGrupoAcessoUsuarioEmpresa = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
};

describe('GrupoAcessoUsuarioEmpresa Service', () => {
  let service: GrupoAcessoUsuarioEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoAcessoUsuarioEmpresa | IGrupoAcessoUsuarioEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoAcessoUsuarioEmpresaService);
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

    it('should create a GrupoAcessoUsuarioEmpresa', () => {
      const grupoAcessoUsuarioEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoAcessoUsuarioEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoAcessoUsuarioEmpresa', () => {
      const grupoAcessoUsuarioEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoAcessoUsuarioEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoAcessoUsuarioEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoAcessoUsuarioEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoAcessoUsuarioEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoAcessoUsuarioEmpresaToCollectionIfMissing', () => {
      it('should add a GrupoAcessoUsuarioEmpresa to an empty array', () => {
        const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing([], grupoAcessoUsuarioEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoUsuarioEmpresa);
      });

      it('should not add a GrupoAcessoUsuarioEmpresa to an array that contains it', () => {
        const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = sampleWithRequiredData;
        const grupoAcessoUsuarioEmpresaCollection: IGrupoAcessoUsuarioEmpresa[] = [
          {
            ...grupoAcessoUsuarioEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing(
          grupoAcessoUsuarioEmpresaCollection,
          grupoAcessoUsuarioEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoAcessoUsuarioEmpresa to an array that doesn't contain it", () => {
        const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = sampleWithRequiredData;
        const grupoAcessoUsuarioEmpresaCollection: IGrupoAcessoUsuarioEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing(
          grupoAcessoUsuarioEmpresaCollection,
          grupoAcessoUsuarioEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoUsuarioEmpresa);
      });

      it('should add only unique GrupoAcessoUsuarioEmpresa to an array', () => {
        const grupoAcessoUsuarioEmpresaArray: IGrupoAcessoUsuarioEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const grupoAcessoUsuarioEmpresaCollection: IGrupoAcessoUsuarioEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing(
          grupoAcessoUsuarioEmpresaCollection,
          ...grupoAcessoUsuarioEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = sampleWithRequiredData;
        const grupoAcessoUsuarioEmpresa2: IGrupoAcessoUsuarioEmpresa = sampleWithPartialData;
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing(
          [],
          grupoAcessoUsuarioEmpresa,
          grupoAcessoUsuarioEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoUsuarioEmpresa);
        expect(expectedResult).toContain(grupoAcessoUsuarioEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing([], null, grupoAcessoUsuarioEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoUsuarioEmpresa);
      });

      it('should return initial array if no GrupoAcessoUsuarioEmpresa is added', () => {
        const grupoAcessoUsuarioEmpresaCollection: IGrupoAcessoUsuarioEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoUsuarioEmpresaToCollectionIfMissing(grupoAcessoUsuarioEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(grupoAcessoUsuarioEmpresaCollection);
      });
    });

    describe('compareGrupoAcessoUsuarioEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoAcessoUsuarioEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoAcessoUsuarioEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoAcessoUsuarioEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoAcessoUsuarioEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoUsuarioEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
