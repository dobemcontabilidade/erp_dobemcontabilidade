import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AvaliacaoService } from '../service/avaliacao.service';
import { IAvaliacao } from '../avaliacao.model';
import { AvaliacaoFormService } from './avaliacao-form.service';

import { AvaliacaoUpdateComponent } from './avaliacao-update.component';

describe('Avaliacao Management Update Component', () => {
  let comp: AvaliacaoUpdateComponent;
  let fixture: ComponentFixture<AvaliacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avaliacaoFormService: AvaliacaoFormService;
  let avaliacaoService: AvaliacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AvaliacaoUpdateComponent],
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
      .overrideTemplate(AvaliacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvaliacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avaliacaoFormService = TestBed.inject(AvaliacaoFormService);
    avaliacaoService = TestBed.inject(AvaliacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const avaliacao: IAvaliacao = { id: 456 };

      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      expect(comp.avaliacao).toEqual(avaliacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoFormService, 'getAvaliacao').mockReturnValue(avaliacao);
      jest.spyOn(avaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacao }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoFormService.getAvaliacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avaliacaoService.update).toHaveBeenCalledWith(expect.objectContaining(avaliacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoFormService, 'getAvaliacao').mockReturnValue({ id: null });
      jest.spyOn(avaliacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avaliacao }));
      saveSubject.complete();

      // THEN
      expect(avaliacaoFormService.getAvaliacao).toHaveBeenCalled();
      expect(avaliacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvaliacao>>();
      const avaliacao = { id: 123 };
      jest.spyOn(avaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avaliacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
