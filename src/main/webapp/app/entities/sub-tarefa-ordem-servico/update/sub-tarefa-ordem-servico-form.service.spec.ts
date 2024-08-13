import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sub-tarefa-ordem-servico.test-samples';

import { SubTarefaOrdemServicoFormService } from './sub-tarefa-ordem-servico-form.service';

describe('SubTarefaOrdemServico Form Service', () => {
  let service: SubTarefaOrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubTarefaOrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createSubTarefaOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });

      it('passing ISubTarefaOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubTarefaOrdemServico', () => {
      it('should return NewSubTarefaOrdemServico for default SubTarefaOrdemServico initial value', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup(sampleWithNewData);

        const subTarefaOrdemServico = service.getSubTarefaOrdemServico(formGroup) as any;

        expect(subTarefaOrdemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubTarefaOrdemServico for empty SubTarefaOrdemServico initial value', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup();

        const subTarefaOrdemServico = service.getSubTarefaOrdemServico(formGroup) as any;

        expect(subTarefaOrdemServico).toMatchObject({});
      });

      it('should return ISubTarefaOrdemServico', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        const subTarefaOrdemServico = service.getSubTarefaOrdemServico(formGroup) as any;

        expect(subTarefaOrdemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubTarefaOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubTarefaOrdemServico should disable id FormControl', () => {
        const formGroup = service.createSubTarefaOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
