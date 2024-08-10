import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CnaeService } from '../service/cnae.service';
import { ICnae } from '../cnae.model';
import { CnaeFormService } from './cnae-form.service';

import { CnaeUpdateComponent } from './cnae-update.component';

describe('Cnae Management Update Component', () => {
  let comp: CnaeUpdateComponent;
  let fixture: ComponentFixture<CnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cnaeFormService: CnaeFormService;
  let cnaeService: CnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CnaeUpdateComponent],
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
      .overrideTemplate(CnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cnaeFormService = TestBed.inject(CnaeFormService);
    cnaeService = TestBed.inject(CnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cnae: ICnae = { id: 456 };

      activatedRoute.data = of({ cnae });
      comp.ngOnInit();

      expect(comp.cnae).toEqual(cnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICnae>>();
      const cnae = { id: 123 };
      jest.spyOn(cnaeFormService, 'getCnae').mockReturnValue(cnae);
      jest.spyOn(cnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cnae }));
      saveSubject.complete();

      // THEN
      expect(cnaeFormService.getCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cnaeService.update).toHaveBeenCalledWith(expect.objectContaining(cnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICnae>>();
      const cnae = { id: 123 };
      jest.spyOn(cnaeFormService, 'getCnae').mockReturnValue({ id: null });
      jest.spyOn(cnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cnae }));
      saveSubject.complete();

      // THEN
      expect(cnaeFormService.getCnae).toHaveBeenCalled();
      expect(cnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICnae>>();
      const cnae = { id: 123 };
      jest.spyOn(cnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
