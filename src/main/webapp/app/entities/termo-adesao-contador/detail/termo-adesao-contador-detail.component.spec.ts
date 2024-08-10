import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TermoAdesaoContadorDetailComponent } from './termo-adesao-contador-detail.component';

describe('TermoAdesaoContador Management Detail Component', () => {
  let comp: TermoAdesaoContadorDetailComponent;
  let fixture: ComponentFixture<TermoAdesaoContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermoAdesaoContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TermoAdesaoContadorDetailComponent,
              resolve: { termoAdesaoContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TermoAdesaoContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TermoAdesaoContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load termoAdesaoContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TermoAdesaoContadorDetailComponent);

      // THEN
      expect(instance.termoAdesaoContador()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
