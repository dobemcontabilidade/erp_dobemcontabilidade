import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../grupo-acesso-empresa.test-samples';

import { GrupoAcessoEmpresaService } from './grupo-acesso-empresa.service';

const requireRestSample: IGrupoAcessoEmpresa = {
  ...sampleWithRequiredData,
};

describe('GrupoAcessoEmpresa Service', () => {
  let service: GrupoAcessoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoAcessoEmpresa | IGrupoAcessoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoAcessoEmpresaService);
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

    it('should create a GrupoAcessoEmpresa', () => {
      const grupoAcessoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoAcessoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoAcessoEmpresa', () => {
      const grupoAcessoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoAcessoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoAcessoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoAcessoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoAcessoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoAcessoEmpresaToCollectionIfMissing', () => {
      it('should add a GrupoAcessoEmpresa to an empty array', () => {
        const grupoAcessoEmpresa: IGrupoAcessoEmpresa = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing([], grupoAcessoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoEmpresa);
      });

      it('should not add a GrupoAcessoEmpresa to an array that contains it', () => {
        const grupoAcessoEmpresa: IGrupoAcessoEmpresa = sampleWithRequiredData;
        const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [
          {
            ...grupoAcessoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing(grupoAcessoEmpresaCollection, grupoAcessoEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoAcessoEmpresa to an array that doesn't contain it", () => {
        const grupoAcessoEmpresa: IGrupoAcessoEmpresa = sampleWithRequiredData;
        const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing(grupoAcessoEmpresaCollection, grupoAcessoEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoEmpresa);
      });

      it('should add only unique GrupoAcessoEmpresa to an array', () => {
        const grupoAcessoEmpresaArray: IGrupoAcessoEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing(grupoAcessoEmpresaCollection, ...grupoAcessoEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoAcessoEmpresa: IGrupoAcessoEmpresa = sampleWithRequiredData;
        const grupoAcessoEmpresa2: IGrupoAcessoEmpresa = sampleWithPartialData;
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing([], grupoAcessoEmpresa, grupoAcessoEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoAcessoEmpresa);
        expect(expectedResult).toContain(grupoAcessoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const grupoAcessoEmpresa: IGrupoAcessoEmpresa = sampleWithRequiredData;
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing([], null, grupoAcessoEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoAcessoEmpresa);
      });

      it('should return initial array if no GrupoAcessoEmpresa is added', () => {
        const grupoAcessoEmpresaCollection: IGrupoAcessoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoAcessoEmpresaToCollectionIfMissing(grupoAcessoEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(grupoAcessoEmpresaCollection);
      });
    });

    describe('compareGrupoAcessoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoAcessoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
