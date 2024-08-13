import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../perfil-acesso-usuario.test-samples';

import { PerfilAcessoUsuarioService, RestPerfilAcessoUsuario } from './perfil-acesso-usuario.service';

const requireRestSample: RestPerfilAcessoUsuario = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
};

describe('PerfilAcessoUsuario Service', () => {
  let service: PerfilAcessoUsuarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilAcessoUsuario | IPerfilAcessoUsuario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilAcessoUsuarioService);
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

    it('should create a PerfilAcessoUsuario', () => {
      const perfilAcessoUsuario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilAcessoUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilAcessoUsuario', () => {
      const perfilAcessoUsuario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilAcessoUsuario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilAcessoUsuario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilAcessoUsuario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilAcessoUsuario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilAcessoUsuarioToCollectionIfMissing', () => {
      it('should add a PerfilAcessoUsuario to an empty array', () => {
        const perfilAcessoUsuario: IPerfilAcessoUsuario = sampleWithRequiredData;
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing([], perfilAcessoUsuario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilAcessoUsuario);
      });

      it('should not add a PerfilAcessoUsuario to an array that contains it', () => {
        const perfilAcessoUsuario: IPerfilAcessoUsuario = sampleWithRequiredData;
        const perfilAcessoUsuarioCollection: IPerfilAcessoUsuario[] = [
          {
            ...perfilAcessoUsuario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing(perfilAcessoUsuarioCollection, perfilAcessoUsuario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilAcessoUsuario to an array that doesn't contain it", () => {
        const perfilAcessoUsuario: IPerfilAcessoUsuario = sampleWithRequiredData;
        const perfilAcessoUsuarioCollection: IPerfilAcessoUsuario[] = [sampleWithPartialData];
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing(perfilAcessoUsuarioCollection, perfilAcessoUsuario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilAcessoUsuario);
      });

      it('should add only unique PerfilAcessoUsuario to an array', () => {
        const perfilAcessoUsuarioArray: IPerfilAcessoUsuario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const perfilAcessoUsuarioCollection: IPerfilAcessoUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing(perfilAcessoUsuarioCollection, ...perfilAcessoUsuarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilAcessoUsuario: IPerfilAcessoUsuario = sampleWithRequiredData;
        const perfilAcessoUsuario2: IPerfilAcessoUsuario = sampleWithPartialData;
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing([], perfilAcessoUsuario, perfilAcessoUsuario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilAcessoUsuario);
        expect(expectedResult).toContain(perfilAcessoUsuario2);
      });

      it('should accept null and undefined values', () => {
        const perfilAcessoUsuario: IPerfilAcessoUsuario = sampleWithRequiredData;
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing([], null, perfilAcessoUsuario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilAcessoUsuario);
      });

      it('should return initial array if no PerfilAcessoUsuario is added', () => {
        const perfilAcessoUsuarioCollection: IPerfilAcessoUsuario[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilAcessoUsuarioToCollectionIfMissing(perfilAcessoUsuarioCollection, undefined, null);
        expect(expectedResult).toEqual(perfilAcessoUsuarioCollection);
      });
    });

    describe('comparePerfilAcessoUsuario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilAcessoUsuario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilAcessoUsuario(entity1, entity2);
        const compareResult2 = service.comparePerfilAcessoUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilAcessoUsuario(entity1, entity2);
        const compareResult2 = service.comparePerfilAcessoUsuario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilAcessoUsuario(entity1, entity2);
        const compareResult2 = service.comparePerfilAcessoUsuario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
