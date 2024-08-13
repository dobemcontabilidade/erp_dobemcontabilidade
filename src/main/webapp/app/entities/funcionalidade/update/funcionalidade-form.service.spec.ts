import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../funcionalidade.test-samples';

import { FuncionalidadeFormService } from './funcionalidade-form.service';

describe('Funcionalidade Form Service', () => {
  let service: FuncionalidadeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuncionalidadeFormService);
  });

  describe('Service methods', () => {
    describe('createFuncionalidadeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuncionalidadeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ativa: expect.any(Object),
            modulo: expect.any(Object),
          }),
        );
      });

      it('passing IFuncionalidade should create a new form with FormGroup', () => {
        const formGroup = service.createFuncionalidadeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ativa: expect.any(Object),
            modulo: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuncionalidade', () => {
      it('should return NewFuncionalidade for default Funcionalidade initial value', () => {
        const formGroup = service.createFuncionalidadeFormGroup(sampleWithNewData);

        const funcionalidade = service.getFuncionalidade(formGroup) as any;

        expect(funcionalidade).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuncionalidade for empty Funcionalidade initial value', () => {
        const formGroup = service.createFuncionalidadeFormGroup();

        const funcionalidade = service.getFuncionalidade(formGroup) as any;

        expect(funcionalidade).toMatchObject({});
      });

      it('should return IFuncionalidade', () => {
        const formGroup = service.createFuncionalidadeFormGroup(sampleWithRequiredData);

        const funcionalidade = service.getFuncionalidade(formGroup) as any;

        expect(funcionalidade).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuncionalidade should not enable id FormControl', () => {
        const formGroup = service.createFuncionalidadeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuncionalidade should disable id FormControl', () => {
        const formGroup = service.createFuncionalidadeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
