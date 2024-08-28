import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PessoaFisicaService } from '../service/pessoa-fisica.service';
import { IPessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaFormService } from './pessoa-fisica-form.service';

import { PessoaFisicaUpdateComponent } from './pessoa-fisica-update.component';

describe('PessoaFisica Management Update Component', () => {
  let comp: PessoaFisicaUpdateComponent;
  let fixture: ComponentFixture<PessoaFisicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pessoaFisicaFormService: PessoaFisicaFormService;
  let pessoaFisicaService: PessoaFisicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PessoaFisicaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PessoaFisicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PessoaFisicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pessoaFisicaFormService = TestBed.inject(PessoaFisicaFormService);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pessoaFisica: IPessoaFisica = { id: 456 };

      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      expect(comp.pessoaFisica).toEqual(pessoaFisica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoaFisica>>();
      const pessoaFisica = { id: 123 };
      jest.spyOn(pessoaFisicaFormService, 'getPessoaFisica').mockReturnValue(pessoaFisica);
      jest.spyOn(pessoaFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoaFisica }));
      saveSubject.complete();

      // THEN
      expect(pessoaFisicaFormService.getPessoaFisica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pessoaFisicaService.update).toHaveBeenCalledWith(expect.objectContaining(pessoaFisica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoaFisica>>();
      const pessoaFisica = { id: 123 };
      jest.spyOn(pessoaFisicaFormService, 'getPessoaFisica').mockReturnValue({ id: null });
      jest.spyOn(pessoaFisicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoaFisica }));
      saveSubject.complete();

      // THEN
      expect(pessoaFisicaFormService.getPessoaFisica).toHaveBeenCalled();
      expect(pessoaFisicaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPessoaFisica>>();
      const pessoaFisica = { id: 123 };
      jest.spyOn(pessoaFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pessoaFisicaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
