import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../docs-pessoa.test-samples';

import { DocsPessoaFormService } from './docs-pessoa-form.service';

describe('DocsPessoa Form Service', () => {
  let service: DocsPessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocsPessoaFormService);
  });

  describe('Service methods', () => {
    describe('createDocsPessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocsPessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlArquivo: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });

      it('passing IDocsPessoa should create a new form with FormGroup', () => {
        const formGroup = service.createDocsPessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlArquivo: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocsPessoa', () => {
      it('should return NewDocsPessoa for default DocsPessoa initial value', () => {
        const formGroup = service.createDocsPessoaFormGroup(sampleWithNewData);

        const docsPessoa = service.getDocsPessoa(formGroup) as any;

        expect(docsPessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocsPessoa for empty DocsPessoa initial value', () => {
        const formGroup = service.createDocsPessoaFormGroup();

        const docsPessoa = service.getDocsPessoa(formGroup) as any;

        expect(docsPessoa).toMatchObject({});
      });

      it('should return IDocsPessoa', () => {
        const formGroup = service.createDocsPessoaFormGroup(sampleWithRequiredData);

        const docsPessoa = service.getDocsPessoa(formGroup) as any;

        expect(docsPessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocsPessoa should not enable id FormControl', () => {
        const formGroup = service.createDocsPessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocsPessoa should disable id FormControl', () => {
        const formGroup = service.createDocsPessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
